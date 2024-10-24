package su.zhenya.me.domain.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.Type;
import su.zhenya.me.domain.entity.type.HospitalIdType;
import su.zhenya.me.hospital.model.HospitalId;

@Data
@Entity
@Table(name = "hospitals")
public class HospitalEntity {

    @Id
    @Type(HospitalIdType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private HospitalId hospitalId;
    private String name;
    private String address;
    private String contactPhone;
    @ElementCollection
    @Column(name = "room")
    @CollectionTable(name = "hospital_rooms", joinColumns = @JoinColumn(name = "hospital_id"))
    private List<String> rooms;
}
