package su.zhenya.me.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.type.AccountIdType;

@Data
@Entity
@Table(name = "account_credentials")
public class AccountCredentialsEntity {

    @Id
    @Type(AccountIdType.class)
    private AccountId accountId;
    private String username;
    private String password;
    @MapsId
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
