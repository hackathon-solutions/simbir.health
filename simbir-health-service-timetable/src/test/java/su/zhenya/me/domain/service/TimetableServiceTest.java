package su.zhenya.me.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.domain.mapper.DoctorTimetableEntityMapper;
import su.zhenya.me.domain.mapper.PatientAppointmentEntityMapper;
import su.zhenya.me.domain.repository.DoctorTimetableRepository;
import su.zhenya.me.domain.repository.PatientAppointmentRepository;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.AppointmentId;
import su.zhenya.me.timetable.model.DoctorTimetable;
import su.zhenya.me.timetable.model.PatientAppointment;
import su.zhenya.me.timetable.model.TimetableId;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TimetableServiceTest {

    private static final DoctorTimetable doctorTimetableForTesting = new DoctorTimetable(
            new TimetableId(UUID.randomUUID()),
            new Hospital(new HospitalId(33)),
            new Account(new AccountId(66)),
            "rooo",
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    private static final PatientAppointment patientAppointmentForTesting = new PatientAppointment(
            new AppointmentId(UUID.randomUUID()),
            new Account(new AccountId(99)),
            doctorTimetableForTesting.getDoctor(),
            doctorTimetableForTesting.getRoom(),
            LocalDateTime.now()
    );

    private static final TimetableId failureTimetable = new TimetableId(UUID.randomUUID());

    private final DoctorTimetableRepository doctorTimetableRepository = mock(DoctorTimetableRepository.class);
    private final DoctorTimetableEntityMapper doctorTimetableEntityMapper = Mappers.getMapper(DoctorTimetableEntityMapper.class);
    private final AppointmentService appointmentService = mock(AppointmentService.class);
    private final PatientAppointmentRepository patientAppointmentRepository = mock(PatientAppointmentRepository.class);
    private final PatientAppointmentEntityMapper patientAppointmentEntityMapper = Mappers.getMapper(PatientAppointmentEntityMapper.class);
    private TimetableService timetableService;

    @BeforeEach
    void init() {
        timetableService = new TimetableService(
                doctorTimetableRepository,
                doctorTimetableEntityMapper,
                appointmentService,
                patientAppointmentRepository,
                patientAppointmentEntityMapper
        );

        given(doctorTimetableRepository.save(any())).will(mock -> mock.getArguments()[0]);

        given(appointmentService.isExistsPatientAppointmentBy(doctorTimetableForTesting.getTimetableId())).willReturn(false);
        given(appointmentService.isExistsPatientAppointmentBy(failureTimetable)).willReturn(true);

        given(patientAppointmentRepository.save(any())).will(mock -> mock.getArguments()[0]);
    }

    @Test
    void saveDoctorTimetableShouldSavingCorrectDoctorTimetable() {
        assertEquals(doctorTimetableForTesting, timetableService.saveDoctorTimetable(doctorTimetableForTesting));
    }

    @Test
    void updateDoctorTimetableShouldUpdateTimetableData() {
        assertEquals(
                doctorTimetableForTesting,
                timetableService.updateDoctorTimetable(
                        doctorTimetableForTesting.getTimetableId(),
                        doctorTimetableForTesting
                )
        );
    }

    @Test
    void deleteDoctorTimetableShouldBeCorrect() {
        timetableService.deleteDoctorTimetable(doctorTimetableForTesting.getTimetableId());
    }

    @Test
    void deleteDoctorTimetablesByAccountShouldBeCorrect() {
        timetableService.deleteDoctorTimetablesBy(doctorTimetableForTesting.getDoctor().getAccountId());
    }

    @Test
    void deleteDoctorTimetablesByHospitalShouldBeCorrect() {
        timetableService.deleteDoctorTimetablesBy(doctorTimetableForTesting.getHospital().getHospitalId());
    }

    @Test
    void createPatientAppointmentShouldSavingCorrectPatientAppointment() {
        PatientAppointment actualPatientAppointment = timetableService.createPatientAppointment(
                doctorTimetableForTesting.getTimetableId(),
                patientAppointmentForTesting.getPatient().getAccountId(),
                patientAppointmentForTesting.getTime()
        );

        actualPatientAppointment.setAppointmentId(patientAppointmentForTesting.getAppointmentId());
        actualPatientAppointment.setDoctor(patientAppointmentForTesting.getDoctor());
        actualPatientAppointment.setRoom(patientAppointmentForTesting.getRoom());

        assertEquals(patientAppointmentForTesting, actualPatientAppointment);
    }

    @Test
    void updateDoctorTimetableShouldThrowException() {
        assertThrows(ReplaceException.class, () -> timetableService.updateDoctorTimetable(failureTimetable, doctorTimetableForTesting));
    }
}
