package su.zhenya.me.timetable.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimetableId {

    private UUID id;

    public static TimetableId randomId() {
        return new TimetableId(UUID.randomUUID());
    }
}
