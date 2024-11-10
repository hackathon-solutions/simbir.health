package su.zhenya.me.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.AccountCredentialsEntity;

@Repository
public interface AccountCredentialsRepository extends JpaRepository<AccountCredentialsEntity, AccountId> {

    AccountCredentialsEntity findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
    AccountCredentialsEntity findByUsername(String username);
}
