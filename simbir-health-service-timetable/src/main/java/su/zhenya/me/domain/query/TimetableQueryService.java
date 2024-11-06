package su.zhenya.me.domain.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.domain.mapper.DoctorTimetableEntityMapper;
import su.zhenya.me.domain.mapper.PatientAppointmentEntityMapper;
import su.zhenya.me.domain.repository.DoctorTimetableRepository;
import su.zhenya.me.domain.repository.PatientAppointmentRepository;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.DoctorTimetable;
import su.zhenya.me.timetable.model.PatientAppointment;
import su.zhenya.me.timetable.model.TimetableId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: сделать обвязку на проверку существования сущностей из других микросервисов
@Service
@RequiredArgsConstructor
public class TimetableQueryService {

    private final DoctorTimetableRepository doctorTimetableRepository;
    private final DoctorTimetableEntityMapper doctorTimetableEntityMapper;
    private final PatientAppointmentRepository patientAppointmentRepository;
    private final PatientAppointmentEntityMapper patientAppointmentEntityMapper;

    public Page<DoctorTimetable> getDoctorsTimetableBy(HospitalId hospitalId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return doctorTimetableRepository.findTimeOverlappingIntervals(hospitalId, from, to, pageable)
                .map(doctorTimetableEntityMapper::entityToDomain);
    }

    public Page<DoctorTimetable> getDoctorsTimetableBy(AccountId doctorId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return doctorTimetableRepository.findTimeOverlappingIntervals(doctorId, from, to, pageable)
                .map(doctorTimetableEntityMapper::entityToDomain);
    }

    public Page<DoctorTimetable> getDoctorsTimetableBy(HospitalId hospitalId, String room, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return doctorTimetableRepository.findTimeOverlappingIntervals(hospitalId, room, from, to, pageable)
                .map(doctorTimetableEntityMapper::entityToDomain);
    }

    public DoctorTimetable getDoctorTimetableBy(TimetableId timetableId) {
        return doctorTimetableRepository.findById(timetableId)
                .map(doctorTimetableEntityMapper::entityToDomain)
                .orElseThrow(ReplaceException::new);
    }

    public List<PatientAppointment> getAvailableForAppointmentBy(TimetableId timetableId, LocalDateTime from, LocalDateTime to) {
        DoctorTimetable doctorTimetable = getDoctorTimetableBy(timetableId);
        List<PatientAppointment> patientAppointments = patientAppointmentRepository.findByTimetableIdWithTimeInterval(timetableId, from, to).stream()
                .map(patientAppointmentEntityMapper::entityToDomain)
                .toList();

        List<PatientAppointment> availableAppointments = new ArrayList<>();

        for (LocalDateTime time = from; time.isBefore(to); time = time.plusMinutes(30)) {
            boolean isTime = false;

            for (PatientAppointment patientAppointment : patientAppointments) {
                if (patientAppointment.getTime().isEqual(time)) {
                    isTime = true;
                    break;
                }
            }

            if (!isTime) {
                availableAppointments.add(new PatientAppointment(null, null, doctorTimetable.getDoctor(), doctorTimetable.getRoom(), time));
            }
        }

        return availableAppointments;
    }
}
