package su.zhenya.me.api.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountId;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailablePatientAppointmentResponse {

    private AccountId doctorId;
    private String room;
    private LocalDateTime time;
}
