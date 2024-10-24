package su.zhenya.me.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.zhenya.me.domain.entity.HospitalEntity;
import su.zhenya.me.domain.entity.mapper.HospitalEntityMapper;
import su.zhenya.me.domain.repository.HospitalEntityRepository;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.hospital.model.HospitalId;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalEntityRepository hospitalEntityRepository;
    private final HospitalEntityMapper hospitalEntityMapper;

    public HospitalId createHospital(Hospital hospital) {
        HospitalEntity hospitalEntity = hospitalEntityMapper.domainToEntity(hospital);
        return hospitalEntityRepository.save(hospitalEntity).getHospitalId();
    }

    public Hospital updateHospitalById(HospitalId hospitalId, Hospital hospital) {
        hospital.setHospitalId(hospitalId);
        HospitalEntity hospitalEntity = hospitalEntityMapper.domainToEntity(hospital);
        return hospitalEntityMapper.entityToDomain(hospitalEntityRepository.save(hospitalEntity));
    }
}
