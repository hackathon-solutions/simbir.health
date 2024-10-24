package su.zhenya.me.domain.query;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.domain.entity.mapper.HospitalEntityMapper;
import su.zhenya.me.domain.repository.HospitalEntityRepository;
import su.zhenya.me.hospital.model.Hospital;
import su.zhenya.me.hospital.model.HospitalId;

@Service
@RequiredArgsConstructor
public class HospitalQueryService {

    private final HospitalEntityRepository hospitalEntityRepository;
    private final HospitalEntityMapper hospitalEntityMapper;

    public Page<Hospital> getAllHospitals(Pageable pageable) {
        return hospitalEntityRepository.findAll(pageable)
                                       .map(hospitalEntityMapper::entityToDomain);
    }

    public Hospital getHospitalById(HospitalId hospitalId) {
        return hospitalEntityMapper.entityToDomain(hospitalEntityRepository.findById(hospitalId).orElseThrow(ReplaceException::new));
    }

    public List<String> getHospitalRoomsByHospitalId(HospitalId hospitalId) {
        return getHospitalById(hospitalId).getRooms();
    }
}
