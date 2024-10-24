package su.zhenya.me.api.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateHospitalRequest {

    private String name;
    private String address;
    private String contactPhone;
}
