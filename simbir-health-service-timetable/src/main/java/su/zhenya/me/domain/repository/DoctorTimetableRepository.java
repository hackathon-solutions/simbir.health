package su.zhenya.me.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.DoctorTimetableEntity;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.TimetableId;

import java.time.LocalDateTime;

public interface DoctorTimetableRepository extends JpaRepository<DoctorTimetableEntity, TimetableId> {

    void deleteByDoctorId(AccountId doctorId);
    void deleteByHospitalId(HospitalId hospitalId);

    @Query("FROM DoctorTimetableEntity e WHERE e.hospitalId = :hospitalId AND (e.from <= :to AND e.to >= :from)")
    Page<DoctorTimetableEntity> findTimeOverlappingIntervals(HospitalId hospitalId, LocalDateTime from, LocalDateTime to, Pageable pageable);

    @Query("FROM DoctorTimetableEntity e WHERE e.doctorId = :doctorId AND (e.from <= :to AND e.to >= :from)")
    Page<DoctorTimetableEntity> findTimeOverlappingIntervals(AccountId doctorId, LocalDateTime from, LocalDateTime to, Pageable pageable);

    @Query("FROM DoctorTimetableEntity e WHERE e.hospitalId = :hospitalId AND e.room = :room AND (e.from <= :to AND e.to >= :from)")
    Page<DoctorTimetableEntity> findTimeOverlappingIntervals(HospitalId hospitalId, String room, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
