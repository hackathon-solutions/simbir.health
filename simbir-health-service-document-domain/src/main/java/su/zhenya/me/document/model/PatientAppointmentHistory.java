package su.zhenya.me.document.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.timetable.model.PatientAppointment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAppointmentHistory {

    private HistoryId historyId;
    private PatientAppointment patientAppointment;
    private String data;
}
