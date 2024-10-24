package su.zhenya.me.api.rest.controller;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.AppointmentId;
import su.zhenya.me.timetable.model.DoctorTimetable;
import su.zhenya.me.timetable.model.PatientAppointment;
import su.zhenya.me.timetable.model.TimetableId;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.timetable.path}")
public class TimetableController {

    @HasRole({Role.ADMIN, Role.MANAGER})
    @PostMapping("${service.rest-api.controllers.timetable.endpoints.create-timetable-post}")
    public TimetableId createTimetable(@RequestBody PatientAppointment patientAppointment) {
        return null;
    }

    @HasRole({Role.ADMIN, Role.MANAGER})
    @PutMapping("${service.rest-api.controllers.timetable.endpoints.patch-timetable-by-id}")
    public PatientAppointment patchTimetableById(@PathVariable AppointmentId appointmentId, @RequestBody PatientAppointment patientAppointment) {
        return null;
    }

    @HasRole({Role.ADMIN, Role.MANAGER})
    @DeleteMapping("${service.rest-api.controllers.timetable.endpoints.delete-timetable-by-id}")
    public void deleteTimetableById(@PathVariable AppointmentId appointmentId) {
    }

    @HasRole({Role.ADMIN, Role.MANAGER})
    @DeleteMapping("${service.rest-api.controllers.timetable.endpoints.delete-timetable-doctor-by-id}")
    public void deleteTimetableDoctorByDoctorId(@PathVariable AccountId doctorId) {
    }

    @HasRole({Role.ADMIN, Role.MANAGER})
    @DeleteMapping("${service.rest-api.controllers.timetable.endpoints.delete-timetable-hospital-by-id}")
    public void deleteTimetableHospitalByHospitalId(@PathVariable HospitalId hospitalId) {
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-all-timetable-hospital-by-id}")
    public Page<DoctorTimetable> getAllTimetableHospitalByHospitalId(
            @PathVariable HospitalId hospitalId,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return null;
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-all-timetable-doctor-by-id}")
    public Page<DoctorTimetable> getAllTimetableDoctorByDoctorId(
            @PathVariable AccountId doctorId,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return null;
    }

    @HasRole({Role.ADMIN, Role.MANAGER, Role.DOCTOR})
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-timetable-hospital-room-by-id}")
    public DoctorTimetable getTimetableHospitalRoomById(
            @PathVariable HospitalId hospitalId,
            @PathVariable String room,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return null;
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-free-appointments-by-doctor-timetable-id}")
    public Page<PatientAppointment> getFreeAppointmentsByDoctorTimetableId(
            @PathVariable TimetableId timetableId,
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to
    ) {
        return null;
    }

    @OnlyAuthorized
    @PostMapping("${service.rest-api.controllers.timetable.endpoints.create-patient-appointment-by-id}")
    public AppointmentId createPatientAppointment(@PathVariable TimetableId timetableId) {
        return null;
    }
}
