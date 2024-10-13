package su.zhenya.me.config;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import su.zhenya.me.config.ServiceConfiguration.Controller;
import su.zhenya.me.domain.entity.mapper.AccountEntityMapper;
import su.zhenya.me.domain.repository.AccountRepository;
import su.zhenya.me.security.config.bean.HttpRequestMatcherRegistryCustomizer;
import su.zhenya.me.security.core.provider.account.AccountProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ServiceConfiguration serviceConfiguration;

    @Bean
    public HttpRequestMatcherRegistryCustomizer httpRequestMatcherRegistryCustomizer() {
        return registry -> registry
                .requestMatchers(getApiPathFor("account", "account-get-all")).hasRole("ADMIN")
                .anyRequest().permitAll();
    }

    private String getApiPathFor(String controllerName, String methodName) {
        Controller controller = serviceConfiguration.getApi().getControllers().get(controllerName);
        String methodPath = controller.getEndpoints().get(methodName);
        return controller.getPath() + methodPath;
    }

    @Bean
    public AccountProvider accountProvider(AccountEntityMapper mapper, AccountRepository repository) {
        return accountId -> Optional.ofNullable(mapper.entityToDomain(repository.findById(accountId).get()));
    }
}
