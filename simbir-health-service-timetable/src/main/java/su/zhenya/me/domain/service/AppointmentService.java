package su.zhenya.me.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.domain.mapper.PatientAppointmentEntityMapper;
import su.zhenya.me.domain.repository.PatientAppointmentRepository;
import su.zhenya.me.timetable.model.AppointmentId;
import su.zhenya.me.timetable.model.PatientAppointment;
import su.zhenya.me.timetable.model.TimetableId;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final PatientAppointmentRepository patientAppointmentRepository;
    private final PatientAppointmentEntityMapper patientAppointmentEntityMapper;

    public boolean isExistsPatientAppointmentBy(TimetableId timetableId) {
        return patientAppointmentRepository.existsByDoctorTimetable_TimetableId(timetableId);
    }

    public void deletePatientAppointment(AppointmentId appointmentId) {
        patientAppointmentRepository.deleteById(appointmentId);
    }

    public PatientAppointment getPatientAppointmentBy(AppointmentId appointmentId) {
        return patientAppointmentEntityMapper.entityToDomain(patientAppointmentRepository.findById(appointmentId).orElseThrow(ReplaceException::new));
    }
}
