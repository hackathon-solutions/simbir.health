package su.zhenya.me.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, AccountId> {

    AccountEntity findByCredentials_Username(String username);

    @Query(value = "select * from accounts as acc where acc.account_roles like concat('%', :role, '%') and concat(acc.first_name, ' ', acc.last_name) like concat('%', :fullName, '%')", nativeQuery = true)
    Page<AccountEntity> findByRolesAndFullName(String role, String fullName, Pageable pageable);

    @Query(value = "select * from accounts as acc where acc.account_roles like concat('%', :role, '%')", nativeQuery = true)
    Page<AccountEntity> findByRole(String role, Pageable pageable);
}
