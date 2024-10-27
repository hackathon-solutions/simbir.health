package su.zhenya.me.api.rest.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.security.bean.authority.annotation.AuthorizationContext;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;
import su.zhenya.me.common.security.core.provider.Authorization;
import su.zhenya.me.domain.service.AppointmentService;
import su.zhenya.me.timetable.model.AppointmentId;
import su.zhenya.me.timetable.model.PatientAppointment;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.appointment.path}")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @HasRole({Role.ADMIN, Role.MANAGER, Role.USER})
    @DeleteMapping("${service.rest-api.controllers.appointment.endpoints.delete-appointment-by-id}")
    public void deleteAppointmentById(
            @PathVariable UUID appointmentId,
            @Schema(hidden = true) @AuthorizationContext Authorization authorization
    ) {
        PatientAppointment patientAppointment = appointmentService.getPatientAppointmentBy(new AppointmentId(appointmentId));

        if (!authorization.getAccountTokenDescriptor().getAccountId().equals(patientAppointment.getPatient().getAccountId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        appointmentService.deletePatientAppointment(patientAppointment.getAppointmentId());
    }
}
