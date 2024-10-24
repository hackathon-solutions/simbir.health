package su.zhenya.me.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import su.zhenya.me.domain.entity.HospitalEntity;
import su.zhenya.me.hospital.model.HospitalId;

@Repository
public interface HospitalEntityRepository extends JpaRepository<HospitalEntity, HospitalId> {

}
