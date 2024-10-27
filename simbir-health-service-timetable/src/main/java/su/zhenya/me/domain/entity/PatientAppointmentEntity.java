package su.zhenya.me.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.type.AccountIdType;
import su.zhenya.me.domain.entity.type.AppointmentIdType;
import su.zhenya.me.timetable.model.AppointmentId;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "patient_appointments")
public class PatientAppointmentEntity {

    @Id
    @Type(AppointmentIdType.class)
    private AppointmentId appointmentId = AppointmentId.randomId();
    @ManyToOne
    @JoinColumn(name = "doctor_timetable_id")
    private DoctorTimetableEntity doctorTimetable;
    @Type(AccountIdType.class)
    private AccountId patientId;
    private LocalDateTime time;
}
