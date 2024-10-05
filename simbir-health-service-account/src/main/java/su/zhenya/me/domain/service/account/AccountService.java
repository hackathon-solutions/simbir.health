package su.zhenya.me.domain.service.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.domain.entity.AccountCredentialsEntity;
import su.zhenya.me.domain.entity.AccountEntity;
import su.zhenya.me.domain.entity.mapper.AccountEntityMapper;
import su.zhenya.me.domain.repository.AccountCredentialsRepository;
import su.zhenya.me.domain.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountEntityMapper accountEntityMapper;
    private final AccountRepository accountRepository;
    private final AccountCredentialsRepository accountCredentialsRepository;

    @Transactional
    public Account saveAccount(Account account) {
        AccountEntity accountEntity = accountEntityMapper.domainToEntity(account);
        AccountCredentialsEntity accountCredentialsEntity = accountEntity.getCredentials();
        accountCredentialsEntity.setAccountId(accountEntity.getAccountId());
        accountEntity = accountRepository.save(accountEntity);
        accountCredentialsEntity.setAccount(accountEntity);
        accountEntity.setCredentials(accountCredentialsRepository.save(accountCredentialsEntity));
        return accountEntityMapper.entityToDomain(accountEntity);
    }

    public void deleteAccount(AccountId accountId) {
        accountRepository.deleteById(accountId);
    }
}
