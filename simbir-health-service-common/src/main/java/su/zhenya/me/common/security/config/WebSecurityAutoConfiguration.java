package su.zhenya.me.common.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import su.zhenya.me.common.security.core.access.AccountTokenService;
import su.zhenya.me.common.security.core.provider.AuthenticationProvider;
import su.zhenya.me.common.security.core.provider.AuthorizationContext;
import su.zhenya.me.common.security.core.provider.account.AccountProvider;
import su.zhenya.me.common.security.core.provider.feign.AuthenticationServerFeignClient;
import su.zhenya.me.common.security.jwt.JWTAccountTokenService;
import su.zhenya.me.common.security.core.access.ServiceSecret;

// TODO: в перспективе перейти на OAuth2 + свой сервер авторизации
@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@EnableConfigurationProperties
@ComponentScan(basePackages = "su.zhenya.me.common.security")
@EnableFeignClients(basePackages = "su.zhenya.me.common.security")
public class WebSecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AuthenticationProvider.class)
    public AuthenticationProvider autoAuthenticationProvider(
            AccountTokenService accountTokenService,
            AuthenticationServerFeignClient authenticationServerFeignClient,
            AuthorizationContext authorizationContext
    ) {
        return new AuthenticationProvider(accountTokenService, authenticationServerFeignClient, authorizationContext);
    }

    @Bean
    public AccountTokenService accountTokenService(AccountProvider accountProvider, ServiceSecret serviceSecret) {
        return new JWTAccountTokenService(accountProvider, serviceSecret);
    }

    @Bean
    @ConditionalOnMissingBean(AccountProvider.class)
    public AccountProvider accountProvider() {
        return accountId -> { throw new UnsupportedOperationException("Not implemented account provider for security"); };
    }

    @Bean
    public ServiceSecret serviceSecret(@Value("${service.authentication.secret.key}") String secret) {
        return () -> secret;
    }

    @Bean
    public AuthorizationContext authorizationContext() {
        return new AuthorizationContext();
    }
}
