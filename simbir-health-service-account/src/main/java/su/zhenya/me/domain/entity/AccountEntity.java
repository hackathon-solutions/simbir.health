package su.zhenya.me.domain.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.domain.entity.converter.AccountIdConverter;
import su.zhenya.me.domain.entity.type.AccountIdType;

@Data
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @Type(AccountIdType.class)
    @Convert(converter = AccountIdConverter.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private AccountId accountId;
    private String firstName;
    private String lastName;
    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "account")
    private AccountCredentialsEntity credentials;
    private Role accountRole = Role.USER;
}
