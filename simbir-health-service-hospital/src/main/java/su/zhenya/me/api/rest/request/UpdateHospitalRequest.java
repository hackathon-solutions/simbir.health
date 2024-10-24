package su.zhenya.me.api.rest.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHospitalRequest {

    private String name;
    private String address;
    private String contactPhone;
    private List<String> rooms;
}
