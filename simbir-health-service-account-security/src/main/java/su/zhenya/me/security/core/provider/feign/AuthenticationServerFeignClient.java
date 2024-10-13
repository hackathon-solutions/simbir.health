package su.zhenya.me.security.core.provider.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import su.zhenya.me.account.model.AccountToken;

@FeignClient(value = "authclient", url = "${service.authentication.authorization-server.server-hostname}")
public interface AuthenticationServerFeignClient {

    @PostMapping("${service.authentication.authorization-server.access-token-release-path}")
    AccountToken releaseAccountToken(@RequestBody ReleaseAccountTokenRequest request);

    @GetMapping("${service.authentication.authorization-server.access-token-verify-path}")
    boolean verifyAccountToken(@RequestParam String accessToken);

    @PutMapping("${service.authentication.authorization-server.access-token-suspend-path}")
    void suspendAccountToken(@RequestBody AccountToken accountToken);
}
