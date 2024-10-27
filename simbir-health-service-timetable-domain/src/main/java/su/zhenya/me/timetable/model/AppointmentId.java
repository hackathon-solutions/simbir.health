package su.zhenya.me.timetable.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentId {

    private UUID id;

    public static AppointmentId randomId() {
        return new AppointmentId(UUID.randomUUID());
    }
}
