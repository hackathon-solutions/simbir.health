package su.zhenya.me.security.core.provider.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;

@FeignClient(value = "auth_client", url = "${authentication.server.server-uri}")
public interface AuthenticationServerFeignClient {

    // TODO: доделать клиент
    @PostMapping("${authentication.server.token-release-path}")
    AccountToken releaseAccountToken(AccountId accountId, AccountCredentials accountCredentials);
}
