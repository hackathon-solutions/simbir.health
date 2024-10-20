package su.zhenya.me.api.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;
import su.zhenya.me.api.rest.mapper.AccountRequestMapper;
import su.zhenya.me.api.rest.request.AccountCreateRequest;
import su.zhenya.me.api.rest.request.ReleaseAccountTokenRequest;
import su.zhenya.me.api.rest.response.AccountTokenResponse;
import su.zhenya.me.domain.service.auth.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.account.api.controllers.authentication.path}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AccountRequestMapper accountRequestMapper;

    @PostMapping("${service.account.api.controllers.authentication.endpoints.account-sign-up}")
    public AccountId signUp(@RequestBody AccountCreateRequest request) {
        return authenticationService.accountCreate(accountRequestMapper.requestToDomain(request)).getAccountId();
    }

    @PostMapping("${service.account.api.controllers.authentication.endpoints.account-sign-in}")
    public AccountTokenResponse signIn(@RequestBody ReleaseAccountTokenRequest request) {
        AccountToken accountToken = authenticationService.releaseToken(request.getAccountId(), request.getCredentials());
        CharSequence refreshToken = authenticationService.releaseRefreshToken(request.getAccountId(), request.getCredentials());
        return new AccountTokenResponse(accountToken, refreshToken);
    }

    @PutMapping("${service.account.api.controllers.authentication.endpoints.token-ban}")
    public void tokenBan(@RequestBody AccountToken accountToken) {
        authenticationService.suspendToken(accountToken);
    }

    @GetMapping("${service.account.api.controllers.authentication.endpoints.token-validate}")
    public boolean tokenValidate(@RequestParam String accessToken) {
        return authenticationService.verifyToken(accessToken);
    }

    @PostMapping("${service.account.api.controllers.authentication.endpoints.token-refresh}")
    public AccountToken tokenRefresh(@RequestParam String refreshToken) {
        return authenticationService.refreshToken(refreshToken);
    }
}
