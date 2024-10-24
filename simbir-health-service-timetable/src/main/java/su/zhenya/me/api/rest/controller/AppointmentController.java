package su.zhenya.me.api.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;
import su.zhenya.me.timetable.model.AppointmentId;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.appointment.path}")
public class AppointmentController {

    @HasRole({Role.ADMIN, Role.MANAGER, Role.USER})
    @DeleteMapping("${service.rest-api.controllers.appointment.endpoints.delete-appointment-by-id}")
    public void deleteAppointmentById(@PathVariable AppointmentId appointmentId) {
    }
}
