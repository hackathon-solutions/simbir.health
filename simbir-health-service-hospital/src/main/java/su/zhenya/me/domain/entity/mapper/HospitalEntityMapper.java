package su.zhenya.me.domain.entity.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.domain.entity.HospitalEntity;
import su.zhenya.me.hospital.model.Hospital;

@Mapper
public interface HospitalEntityMapper {

    HospitalEntity domainToEntity(Hospital hospital);
    Hospital entityToDomain(HospitalEntity entity);
}
