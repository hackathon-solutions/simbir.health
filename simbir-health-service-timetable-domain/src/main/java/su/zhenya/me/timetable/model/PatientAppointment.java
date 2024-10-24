package su.zhenya.me.timetable.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.Account;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAppointment {

    private AppointmentId appointmentId;
    private Account patient;
    private Account doctor;
    private String room;
    private LocalDateTime time;
}
