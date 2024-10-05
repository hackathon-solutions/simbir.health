package su.zhenya.me.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountToken {

    private AccountId accountIdBind;
    private CharSequence tokenValue;
}
