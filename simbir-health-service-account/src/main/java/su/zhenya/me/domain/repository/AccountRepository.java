package su.zhenya.me.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, AccountId> {

}
