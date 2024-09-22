package su.zhenya.me.timetable.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.hospital.model.Doctor;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.hospital.model.Room;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTimetable {

    private Hospital hospital;
    private Doctor doctor;
    private Room room;
    private LocalDateTime from;
    private LocalDateTime to;
}
