package su.zhenya.me.api.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountToken;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTokenResponse {

    private AccountToken accessToken;
    private CharSequence refreshToken;
}
