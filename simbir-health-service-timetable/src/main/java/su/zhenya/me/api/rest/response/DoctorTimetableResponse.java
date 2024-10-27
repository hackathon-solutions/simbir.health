package su.zhenya.me.api.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.hospital.model.HospitalId;
import su.zhenya.me.timetable.model.TimetableId;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTimetableResponse {

    private TimetableId timetableId;
    private HospitalId hospitalId;
    private AccountId doctorId;
    private String room;
    private LocalDateTime from;
    private LocalDateTime to;
}
