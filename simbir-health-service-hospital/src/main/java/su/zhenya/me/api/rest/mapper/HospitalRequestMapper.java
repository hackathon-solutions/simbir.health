package su.zhenya.me.api.rest.mapper;

import org.mapstruct.Mapper;
import su.zhenya.me.api.rest.request.CreateHospitalRequest;
import su.zhenya.me.api.rest.request.UpdateHospitalRequest;
import su.zhenya.me.hospital.model.Hospital;

@Mapper
public interface HospitalRequestMapper {

    Hospital requestToDomain(CreateHospitalRequest request);
    Hospital requestToDomain(UpdateHospitalRequest request);
}
