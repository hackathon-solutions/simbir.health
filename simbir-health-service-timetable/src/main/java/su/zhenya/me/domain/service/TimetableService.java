package su.zhenya.me.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.domain.entity.DoctorTimetableEntity;
import su.zhenya.me.domain.entity.PatientAppointmentEntity;
import su.zhenya.me.domain.mapper.DoctorTimetableEntityMapper;
import su.zhenya.me.domain.mapper.PatientAppointmentEntityMapper;
import su.zhenya.me.domain.repository.DoctorTimetableRepository;
import su.zhenya.me.domain.repository.PatientAppointmentRepository;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.DoctorTimetable;
import su.zhenya.me.timetable.model.PatientAppointment;
import su.zhenya.me.timetable.model.TimetableId;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final DoctorTimetableRepository doctorTimetableRepository;
    private final DoctorTimetableEntityMapper doctorTimetableEntityMapper;
    private final AppointmentService appointmentService;
    private final PatientAppointmentRepository patientAppointmentRepository;
    private final PatientAppointmentEntityMapper patientAppointmentEntityMapper;

    public DoctorTimetable saveDoctorTimetable(DoctorTimetable doctorTimetable) {
        DoctorTimetableEntity doctorTimetableEntity = doctorTimetableEntityMapper.domainToEntity(doctorTimetable);
        doctorTimetableEntity.setTimetableId(isNull(doctorTimetable.getTimetableId()) ? TimetableId.randomId() : doctorTimetable.getTimetableId());
        return doctorTimetableEntityMapper.entityToDomain(doctorTimetableRepository.save(doctorTimetableEntity));
    }

    public DoctorTimetable updateDoctorTimetable(TimetableId timetableId, DoctorTimetable doctorTimetable) {
        if (appointmentService.isExistsPatientAppointmentBy(timetableId)) {
            throw new ReplaceException();
        }

        doctorTimetable.setTimetableId(timetableId);
        return saveDoctorTimetable(doctorTimetable);
    }

    public void deleteDoctorTimetable(TimetableId timetableId) {
        doctorTimetableRepository.deleteById(timetableId);
    }

    public void deleteDoctorTimetablesBy(AccountId doctorId) {
        doctorTimetableRepository.deleteByDoctorId(doctorId);
    }

    public void deleteDoctorTimetablesBy(HospitalId hospitalId) {
        doctorTimetableRepository.deleteByHospitalId(hospitalId);
    }

    public PatientAppointment createPatientAppointment(TimetableId timetableId, AccountId patientId, LocalDateTime time) {
        PatientAppointmentEntity patientAppointmentEntity = new PatientAppointmentEntity();
        DoctorTimetableEntity doctorTimetableEntity = new DoctorTimetableEntity();
        doctorTimetableEntity.setTimetableId(timetableId);
        patientAppointmentEntity.setDoctorTimetable(doctorTimetableEntity);
        patientAppointmentEntity.setPatientId(patientId);
        patientAppointmentEntity.setTime(time);
        return patientAppointmentEntityMapper.entityToDomain(patientAppointmentRepository.save(patientAppointmentEntity));
    }
}
