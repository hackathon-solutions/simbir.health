package su.zhenya.me.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import su.zhenya.me.account.model.Role;

@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private long accountId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role accountRole;
}
