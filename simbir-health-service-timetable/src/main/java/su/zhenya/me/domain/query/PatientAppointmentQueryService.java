package su.zhenya.me.domain.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.mapper.PatientAppointmentEntityMapper;
import su.zhenya.me.domain.repository.PatientAppointmentRepository;
import su.zhenya.me.timetable.model.PatientAppointment;

@Service
@RequiredArgsConstructor
public class PatientAppointmentQueryService {

    private final PatientAppointmentRepository patientAppointmentRepository;
    private final PatientAppointmentEntityMapper patientAppointmentEntityMapper;

    public Page<PatientAppointment> getPatientAppointments(AccountId patientId, Pageable pageable) {
        return patientAppointmentRepository.findByPatientId(patientId, pageable)
                .map(patientAppointmentEntityMapper::entityToDomain);
    }
}
