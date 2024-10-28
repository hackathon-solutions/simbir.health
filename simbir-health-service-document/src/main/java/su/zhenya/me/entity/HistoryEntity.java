package su.zhenya.me.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import su.zhenya.me.document.model.HistoryId;
import su.zhenya.me.timetable.model.PatientAppointment;

@Data
@Document(indexName = "history")
public class HistoryEntity {

    @Id
    private HistoryId historyId;
    @Field(type = FieldType.Nested, includeInParent = true)
    private PatientAppointment patientAppointment;
    private String data;
}
