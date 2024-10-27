package su.zhenya.me.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import org.hibernate.annotations.Type;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.type.AccountIdType;
import su.zhenya.me.domain.entity.type.HospitalIdType;
import su.zhenya.me.domain.entity.type.TimetableIdType;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.TimetableId;

@Data
@Entity
@Table(name = "doctor_timetables")
public class DoctorTimetableEntity {

    @Id
    @Type(TimetableIdType.class)
    private TimetableId timetableId = TimetableId.randomId();
    @Type(HospitalIdType.class)
    private HospitalId hospitalId;
    @Type(AccountIdType.class)
    private AccountId doctorId;
    private String room;
    @Column(name = "from_at")
    private LocalDateTime from;
    @Column(name = "to_at")
    private LocalDateTime to;
    @OneToMany(mappedBy = "doctorTimetable", fetch = FetchType.LAZY)
    private List<PatientAppointmentEntity> patientAppointments;
}
