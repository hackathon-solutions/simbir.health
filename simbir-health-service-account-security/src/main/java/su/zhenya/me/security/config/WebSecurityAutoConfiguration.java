package su.zhenya.me.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import su.zhenya.me.security.config.bean.CorsConfigurer;
import su.zhenya.me.security.config.bean.CsrfConfigurer;
import su.zhenya.me.security.config.bean.HttpRequestMatcherRegistryCustomizer;
import su.zhenya.me.security.config.bean.SecurityFilterChainConfigurer;
import su.zhenya.me.security.core.access.AccountTokenService;
import su.zhenya.me.security.core.provider.AuthenticationProvider;
import su.zhenya.me.security.core.provider.account.AccountProvider;
import su.zhenya.me.security.core.provider.feign.AuthenticationServerFeignClient;
import su.zhenya.me.security.jwt.JWTAccountTokenService;
import su.zhenya.me.security.jwt.ServiceSecret;

// TODO: в перспективе перейти на OAuth2 + свой сервер авторизации
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties
@ComponentScan(basePackages = "su.zhenya.me.security")
@EnableFeignClients(basePackages = "su.zhenya.me.security")
public class WebSecurityAutoConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            HttpRequestMatcherRegistryCustomizer authorizeHttpRequestsCustomizer,
            SecurityFilterChainConfigurer securityFilterChainConfigurer,
            CsrfConfigurer csrfConfigurer,
            CorsConfigurer corsConfigurer,
            AuthenticationProvider authenticationProvider
    ) throws Exception {
        securityFilterChainConfigurer.configure(http);

        return http
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer)
                .addFilterBefore(authenticationProvider, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrfConfigurer)
                .cors(corsConfigurer)
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(HttpRequestMatcherRegistryCustomizer.class)
    public HttpRequestMatcherRegistryCustomizer defaultAuthorizeHttpRequestsCustomizer() {
        return registry -> registry
                .anyRequest()
                .permitAll();
    }

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChainConfigurer.class)
    public SecurityFilterChainConfigurer defaultSecurityFilterChainConfigurer() {
        return registry -> {};
    }

    @Bean
    @ConditionalOnMissingBean(CsrfConfigurer.class)
    public CsrfConfigurer defaultCsrfConfigurer() {
        return AbstractHttpConfigurer::disable;
    }

    @Bean
    @ConditionalOnMissingBean(CorsConfigurer.class)
    public CorsConfigurer defaultCorsConfigurer() {
        return AbstractHttpConfigurer::disable;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationProvider.class)
    public AuthenticationProvider autoAuthenticationProvider(
            AccountTokenService accountTokenService,
            AuthenticationServerFeignClient authenticationServerFeignClient
    ) {
        return new AuthenticationProvider(accountTokenService, authenticationServerFeignClient);
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
}
