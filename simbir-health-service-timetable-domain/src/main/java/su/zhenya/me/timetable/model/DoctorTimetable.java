package su.zhenya.me.timetable.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.hospital.model.Hospital;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTimetable {

    private TimetableId timetableId;
    private Hospital hospital;
    private Account doctor;
    private String room;
    private LocalDateTime from;
    private LocalDateTime to;
}
