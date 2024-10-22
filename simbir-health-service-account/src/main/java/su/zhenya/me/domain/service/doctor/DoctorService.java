package su.zhenya.me.domain.service.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.domain.query.AccountQueryService;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final AccountQueryService accountQueryService;

    public Page<Account> getAllAccountsFilteredBy(String filterName, Pageable pageable) {
        return accountQueryService.getAccountsFiltered(Role.DOCTOR, filterName, pageable);
    }

    public Account getDoctorBy(AccountId accountId) {
        Account account = accountQueryService.getAccountBy(accountId);

        if (!account.getRoles().contains(Role.DOCTOR)) {
            throw new ReplaceException();
        }

        return account;
    }
}
