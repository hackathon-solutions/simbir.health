package su.zhenya.me.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import su.zhenya.me.domain.entity.PatientAppointmentEntity;
import su.zhenya.me.timetable.model.AppointmentId;
import su.zhenya.me.timetable.model.TimetableId;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientAppointmentRepository extends JpaRepository<PatientAppointmentEntity, AppointmentId> {

    boolean existsByDoctorTimetable_TimetableId(TimetableId timetableId);

    @Query("FROM PatientAppointmentEntity e WHERE e.doctorTimetable.timetableId = :timetableId AND (e.time >= :from AND e.time <= :to)")
    List<PatientAppointmentEntity> findByTimetableIdWithTimeInterval(TimetableId timetableId, LocalDateTime from, LocalDateTime to);
}
