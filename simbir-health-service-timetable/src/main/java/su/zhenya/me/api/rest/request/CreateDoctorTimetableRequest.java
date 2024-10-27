package su.zhenya.me.api.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.hospital.model.HospitalId;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDoctorTimetableRequest {

    @NotNull
    private HospitalId hospitalId;
    @NotNull
    private AccountId doctorId;
    @NotNull
    private String room;
    @NotNull
    private LocalDateTime from;
    @NotNull
    private LocalDateTime to;
}
