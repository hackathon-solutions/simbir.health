package su.zhenya.me.document.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryId {

    private UUID id;

    public static HistoryId randomId() {
        return new HistoryId(UUID.randomUUID());
    }
}
