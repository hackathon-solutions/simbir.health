package su.zhenya.me.hospital.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {

    private HospitalId hospitalId;
    private String name;
    private String address;
    private String contactPhone;
    private List<String> rooms;

    public Hospital(HospitalId hospitalId) {
        this.hospitalId = hospitalId;
    }
}
