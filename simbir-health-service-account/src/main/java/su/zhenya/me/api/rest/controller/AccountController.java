package su.zhenya.me.api.rest.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.api.rest.mapper.AccountRequestMapper;
import su.zhenya.me.api.rest.mapper.AccountResponseMapper;
import su.zhenya.me.api.rest.request.AccountCreateRequest;
import su.zhenya.me.api.rest.request.AccountSelfUpdateRequest;
import su.zhenya.me.api.rest.request.AccountUpdateRequest;
import su.zhenya.me.api.rest.response.AccountResponse;
import su.zhenya.me.common.security.bean.authority.annotation.AuthorizationContext;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.common.security.core.access.AccountTokenService;
import su.zhenya.me.common.security.core.provider.Authorization;
import su.zhenya.me.domain.query.AccountQueryService;
import su.zhenya.me.domain.service.account.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.account.path}")
public class AccountController {

    private final AccountResponseMapper accountResponseMapper;
    private final AccountRequestMapper accountRequestMapper;
    private final AccountService accountService;
    private final AccountQueryService accountQueryService;
    private final AccountTokenService accountTokenService;

    @OnlyAuthorized
    @GetMapping("${service.rest-api.controllers.account.endpoints.account-get-current}")
    public AccountResponse accountGetCurrent(@Parameter(hidden = true) @AuthorizationContext Authorization authorization) {
        Account account = accountQueryService
                .getAccountBy(accountTokenService.getAccountToken(authorization.getAccessToken()).getAccountIdBind());
        return accountResponseMapper.domainToResponse(account);
    }

    @OnlyAuthorized
    @PutMapping("${service.rest-api.controllers.account.endpoints.account-patch-current}")
    public AccountResponse accountPatchCurrent(
            @RequestBody AccountSelfUpdateRequest request,
            @Parameter(hidden = true) @AuthorizationContext Authorization authorization
    ) {
        Account account = accountRequestMapper.requestToDomain(request);
        account.setAccountId(authorization.getAccountTokenDescriptor().getAccountId());
        return accountResponseMapper.domainToResponse(accountService.saveAccount(account));
    }

    @GetMapping("${service.rest-api.controllers.account.endpoints.account-get-all}")
    @HasRole(Role.ADMIN)
    public Page<AccountResponse> accountGetAll(@ParameterObject Pageable pageable) {
        return accountQueryService.getAccounts(pageable).map(accountResponseMapper::domainToResponse);
    }

    @PostMapping("${service.rest-api.controllers.account.endpoints.account-create}")
    @HasRole(Role.ADMIN)
    public AccountId accountCreate(@RequestBody AccountCreateRequest request) {
        return accountResponseMapper.domainToResponse(accountService.saveAccount(accountRequestMapper.requestToDomain(request))).getAccountId();
    }

    @HasRole(Role.ADMIN)
    @PutMapping("${service.rest-api.controllers.account.endpoints.account-put-by-id}")
    public AccountResponse accountPatchById(@PathVariable long accountId, @RequestBody AccountUpdateRequest request) {
        Account account = accountRequestMapper.requestToDomain(request);
        account.setAccountId(new AccountId(accountId));
        return accountResponseMapper.domainToResponse(accountService.editAccount(account));
    }

    // TODO: реализовать "мягкое" удаление
    @HasRole(Role.ADMIN)
    @DeleteMapping("${service.rest-api.controllers.account.endpoints.account-delete-by-id}")
    public void accountDeleteById(@PathVariable long accountId) {
        accountService.deleteAccount(new AccountId(accountId));
    }

    @GetMapping("${service.rest-api.controllers.account.endpoints.account-get-by-role}")
    public Page<AccountResponse> accountGetByRole(@PathVariable Role role, @ParameterObject Pageable pageable) {
        return accountQueryService.getAccountsFiltered(role, pageable).map(accountResponseMapper::domainToResponse);
    }
}
