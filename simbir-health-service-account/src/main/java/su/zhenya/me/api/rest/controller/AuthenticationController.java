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
import su.zhenya.me.api.rest.request.AccountSignUpRequest;
import su.zhenya.me.api.rest.request.AccountTokenRequest;
import su.zhenya.me.api.rest.request.ReleaseAccountTokenRequest;
import su.zhenya.me.api.rest.response.AccountTokenResponse;
import su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized;
import su.zhenya.me.domain.service.auth.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${service.rest-api.controllers.authentication.path}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AccountRequestMapper accountRequestMapper;

    @PostMapping("${service.rest-api.controllers.authentication.endpoints.account-sign-up}")
    public AccountId signUp(@RequestBody AccountSignUpRequest request) {
        return authenticationService.accountCreate(accountRequestMapper.requestToDomain(request)).getAccountId();
    }

    @PostMapping("${service.rest-api.controllers.authentication.endpoints.account-sign-in}")
    public AccountTokenResponse signIn(@RequestBody ReleaseAccountTokenRequest request) {
        AccountToken accountToken = authenticationService.releaseToken(request.getCredentials());
        CharSequence refreshToken = authenticationService.releaseRefreshToken(request.getCredentials());
        return new AccountTokenResponse(accountToken, refreshToken);
    }

    @OnlyAuthorized
    @PutMapping("${service.rest-api.controllers.authentication.endpoints.token-ban}")
    public void tokenSuspend(@RequestBody AccountTokenRequest accountToken) {
        authenticationService.suspendToken(accountRequestMapper.requestToDomain(accountToken));
    }

    @GetMapping("${service.rest-api.controllers.authentication.endpoints.token-validate}")
    public boolean tokenValidate(@RequestParam String accessToken) {
        return authenticationService.verifyToken(accessToken);
    }

    @PostMapping("${service.rest-api.controllers.authentication.endpoints.token-refresh}")
    public AccountToken tokenRefresh(@RequestParam String refreshToken) {
        return authenticationService.refreshToken(refreshToken);
    }
}
