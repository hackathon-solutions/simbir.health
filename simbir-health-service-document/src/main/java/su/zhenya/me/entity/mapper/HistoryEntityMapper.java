package su.zhenya.me.entity.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.document.model.HistoryId;
import su.zhenya.me.document.model.PatientAppointmentHistory;
import su.zhenya.me.entity.HistoryEntity;

@Mapper(imports = {HistoryId.class})
public interface HistoryEntityMapper {

    PatientAppointmentHistory entityToDomain(HistoryEntity entity);
    HistoryEntity domainToEntity(PatientAppointmentHistory domain);
}
