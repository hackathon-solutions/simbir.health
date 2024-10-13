package su.zhenya.me.api.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.api.rest.header.AuthorizationHeader;
import su.zhenya.me.api.rest.mapper.AccountRequestMapper;
import su.zhenya.me.api.rest.mapper.AccountResponseMapper;
import su.zhenya.me.api.rest.request.AccountCreateRequest;
import su.zhenya.me.api.rest.request.AccountUpdateRequest;
import su.zhenya.me.api.rest.response.AccountResponse;
import su.zhenya.me.domain.query.AccountQueryService;
import su.zhenya.me.domain.service.account.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.account.api.controllers.account.path}")
public class AccountController {

    private final AccountResponseMapper accountResponseMapper;
    private final AccountRequestMapper accountRequestMapper;
    private final AccountService accountService;
    private final AccountQueryService accountQueryService;

    @GetMapping("${service.account.api.controllers.account.endpoints.account-get-current}")
    public void accountGetCurrent(@RequestHeader AuthorizationHeader authorization) {
        System.out.println(authorization.getAuthorizationToken());
    }

    @PutMapping("${service.account.api.controllers.account.endpoints.account-patch-current}")
    public void accountPatchCurrent(@RequestHeader AuthorizationHeader authorization) {
        System.out.println(authorization.getAuthorizationToken());
    }

    @GetMapping("${service.account.api.controllers.account.endpoints.account-get-all}")
    public Page<AccountResponse> accountGetAll(Pageable pageable) {
        return accountQueryService.getAccounts(pageable).map(accountResponseMapper::domainToResponse);
    }

    @PostMapping("${service.account.api.controllers.account.endpoints.account-create}")
    public AccountId accountCreate(@RequestBody AccountCreateRequest request) {
        return accountResponseMapper.domainToResponse(accountService.saveAccount(accountRequestMapper.requestToDomain(request))).getAccountId();
    }

    @PutMapping("${service.account.api.controllers.account.endpoints.account-put-by-id}")
    public AccountResponse accountPatchById(@PathVariable long accountId, @RequestBody AccountUpdateRequest request) {
        Account account = accountRequestMapper.requestToDomain(request);
        account.setAccountId(new AccountId(accountId));
        return accountResponseMapper.domainToResponse(accountService.saveAccount(account));
    }

    // TODO: реализовать "мягкое" удаление
    @DeleteMapping("${service.account.api.controllers.account.endpoints.account-delete-by-id}")
    public void accountDeleteById(@PathVariable long accountId) {
        accountService.deleteAccount(new AccountId(accountId));
    }
}
