package su.zhenya.me.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import su.zhenya.me.security.config.bean.CorsConfigurer;
import su.zhenya.me.security.config.bean.CsrfConfigurer;
import su.zhenya.me.security.config.bean.HttpRequestMatcherRegistryCustomizer;
import su.zhenya.me.security.config.bean.SecurityFilterChainConfigurer;

// TODO: в перспективе перейти на OAuth2 + свой сервер авторизации
@Configuration
@EnableWebSecurity
public class WebSecurityAutoConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            HttpRequestMatcherRegistryCustomizer authorizeHttpRequestsCustomizer,
            SecurityFilterChainConfigurer securityFilterChainConfigurer,
            CsrfConfigurer csrfConfigurer,
            CorsConfigurer corsConfigurer
    ) throws Exception {
        securityFilterChainConfigurer.configure(http);

        // TODO: переписать на filter для аутентификации по jwt через header
        return http
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer)
                .csrf(csrfConfigurer)
                .cors(corsConfigurer)
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
}
