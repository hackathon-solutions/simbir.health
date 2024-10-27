package su.zhenya.me.api.rest.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.api.rest.mapper.AppointmentResponseMapper;
import su.zhenya.me.api.rest.mapper.TimetableRequestMapper;
import su.zhenya.me.api.rest.mapper.TimetableResponseMapper;
import su.zhenya.me.api.rest.request.CreateDoctorTimetableRequest;
import su.zhenya.me.api.rest.request.UpdateDoctorTimetableRequest;
import su.zhenya.me.api.rest.request.validation.CreateDoctorTimetableRequestValidation;
import su.zhenya.me.api.rest.request.validation.UpdateDoctorTimetableRequestValidation;
import su.zhenya.me.api.rest.response.AvailablePatientAppointmentResponse;
import su.zhenya.me.api.rest.response.DoctorTimetableResponse;
import su.zhenya.me.common.security.bean.authority.annotation.AuthorizationContext;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.common.security.core.provider.Authorization;
import su.zhenya.me.common.validation.bean.annotation.Validate;
import su.zhenya.me.common.validation.bean.annotation.ValidateMethod;
import su.zhenya.me.common.validation.core.ValidationException;
import su.zhenya.me.domain.query.TimetableQueryService;
import su.zhenya.me.domain.service.TimetableService;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.AppointmentId;
import su.zhenya.me.timetable.model.DoctorTimetable;
import su.zhenya.me.timetable.model.TimetableId;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

// TODO: переделать потом контракт контроллера нормально
@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.timetable.path}")
public class TimetableController {

    private final TimetableService timetableService;
    private final TimetableQueryService timetableQueryService;
    private final TimetableRequestMapper timetableRequestMapper;
    private final TimetableResponseMapper timetableResponseMapper;
    private final AppointmentResponseMapper appointmentResponseMapper;

    @ValidateMethod
    @HasRole({Role.ADMIN, Role.MANAGER})
    @PostMapping("${service.rest-api.controllers.timetable.endpoints.create-doctor-timetable-post}")
    public TimetableId createDoctorTimetable(
            @RequestBody @Validate(validationClass = CreateDoctorTimetableRequestValidation.class) CreateDoctorTimetableRequest request
    ) {
        DoctorTimetable doctorTimetable = timetableRequestMapper.requestToDomain(request);
        return timetableService.saveDoctorTimetable(doctorTimetable).getTimetableId();
    }

    @ValidateMethod
    @HasRole({Role.ADMIN, Role.MANAGER})
    @PutMapping("${service.rest-api.controllers.timetable.endpoints.patch-doctor-timetable-by-id}")
    public DoctorTimetableResponse patchTimetableById(
            @PathVariable UUID timetableId,
            @Validate(validationClass = UpdateDoctorTimetableRequestValidation.class) @RequestBody UpdateDoctorTimetableRequest request
    ) {
        DoctorTimetable doctorTimetable = timetableRequestMapper.requestToDomain(request);
        return timetableResponseMapper.domainToResponse(timetableService.updateDoctorTimetable(new TimetableId(timetableId), doctorTimetable));
    }

    @HasRole({Role.ADMIN, Role.MANAGER})
    @DeleteMapping("${service.rest-api.controllers.timetable.endpoints.delete-doctor-timetable-by-id}")
    public void deleteTimetableById(@PathVariable UUID timetableId) {
        timetableService.deleteDoctorTimetable(new TimetableId(timetableId));
    }

    @HasRole({Role.ADMIN, Role.MANAGER})
    @DeleteMapping("${service.rest-api.controllers.timetable.endpoints.delete-doctor-timetable-by-doctor-id}")
    public void deleteTimetableDoctorByDoctorId(@PathVariable long doctorId) {
        timetableService.deleteDoctorTimetablesBy(new AccountId(doctorId));
    }

    @HasRole({Role.ADMIN, Role.MANAGER})
    @DeleteMapping("${service.rest-api.controllers.timetable.endpoints.delete-timetable-hospital-by-id}")
    public void deleteTimetableHospitalByHospitalId(@PathVariable HospitalId hospitalId) {
        timetableService.deleteDoctorTimetablesBy(hospitalId);
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-all-timetable-hospital-by-id}")
    public Page<DoctorTimetableResponse> getAllDoctorsTimetableHospitalByHospitalId(
            @PathVariable long hospitalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @ParameterObject Pageable pageable
    ) {
        return timetableQueryService.getDoctorsTimetableBy(new HospitalId(hospitalId), from, to, pageable)
                .map(timetableResponseMapper::domainToResponse);
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-all-timetable-doctor-by-id}")
    public Page<DoctorTimetableResponse> getAllTimetableDoctorByDoctorId(
            @PathVariable long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @ParameterObject Pageable pageable
    ) {
        return timetableQueryService.getDoctorsTimetableBy(new AccountId(doctorId), from, to, pageable)
                .map(timetableResponseMapper::domainToResponse);
    }

    @HasRole({Role.ADMIN, Role.MANAGER, Role.DOCTOR})
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-timetable-hospital-room-by-id}")
    public Page<DoctorTimetableResponse> getTimetableHospitalRoomById(
            @PathVariable long hospitalId,
            @PathVariable String room,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @ParameterObject Pageable pageable
    ) {
        return timetableQueryService.getDoctorsTimetableBy(new HospitalId(hospitalId), room, from, to, pageable)
                .map(timetableResponseMapper::domainToResponse);
    }

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.timetable.endpoints.get-free-appointments-by-doctor-timetable-id}")
    public List<AvailablePatientAppointmentResponse> getFreeAppointmentsByDoctorTimetableId(
            @PathVariable UUID timetableId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) throws ValidationException {
        validateFromToDate(from, to);
        return timetableQueryService.getAvailableForAppointmentBy(new TimetableId(timetableId), from, to).stream()
                .map(appointmentResponseMapper::domainToResponse)
                .toList();
    }

    private void validateFromToDate(LocalDateTime from, LocalDateTime to) throws ValidationException {
        Predicate<LocalDateTime> isNotTimeMultiple30Minutes = time -> time.getMinute() % 30 != 0 && time.getSecond() != 0 && time.getNano() != 0;
        BiPredicate<LocalDateTime, LocalDateTime> isTimesBetweenTooLarge = (startTime, endTime) -> Duration.between(startTime, endTime).get(ChronoUnit.SECONDS) > Duration.ofHours(12).get(ChronoUnit.SECONDS);

        if (isNotTimeMultiple30Minutes.test(from) || isNotTimeMultiple30Minutes.test(to) || isTimesBetweenTooLarge.test(from, to)) {
            throw new ValidationException();
        }
    }

    @OnlyAuthorized
    @PostMapping("${service.rest-api.controllers.timetable.endpoints.create-patient-appointment-by-id}")
    public AppointmentId createPatientAppointment(
            @PathVariable UUID timetableId,
            @RequestParam LocalDateTime time,
            @Schema(hidden = true) @AuthorizationContext Authorization authorization
    ) {
        return timetableService.createPatientAppointment(
                new TimetableId(timetableId),
                authorization.getAccountTokenDescriptor().getAccountId(),
                time
        ).getAppointmentId();
    }
}
