package su.zhenya.me.domain.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.domain.entity.mapper.AccountEntityMapper;
import su.zhenya.me.domain.repository.AccountRepository;
import su.zhenya.me.common.exception.ReplaceException;

@Service
@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountEntityMapper accountEntityMapper;
    private final AccountRepository accountRepository;

    public Page<Account> getAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountEntityMapper::entityToDomain);
    }

    public Account getAccountBy(AccountId accountId) {
        return accountRepository.findById(accountId)
                                .map(accountEntityMapper::entityToDomain)
                                .orElseThrow(ReplaceException::new);
    }

    public Page<Account> getAccountsFiltered(Role role, String fullNameFilter, Pageable pageable) {
        return accountRepository.findByRolesAndFullName(role.toString(), fullNameFilter, pageable)
                                .map(accountEntityMapper::entityToDomain);
    }

    public Page<Account> getAccountsFiltered(Role role, Pageable pageable) {
        return accountRepository.findByRole(role.toString(), pageable)
                .map(accountEntityMapper::entityToDomain);
    }
}
