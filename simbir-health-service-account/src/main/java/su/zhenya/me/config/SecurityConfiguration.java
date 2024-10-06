package su.zhenya.me.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import su.zhenya.me.config.ServiceConfiguration.Controller;
import su.zhenya.me.security.config.bean.HttpRequestMatcherRegistryCustomizer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ServiceConfiguration serviceConfiguration;

    @Bean
    public HttpRequestMatcherRegistryCustomizer httpRequestMatcherRegistryCustomizer() {
        return registry -> registry
                .requestMatchers(getApiPathFor("account", "account-get-all")).denyAll()
                .anyRequest().permitAll();
    }

    private String getApiPathFor(String controllerName, String methodName) {
        Controller controller = serviceConfiguration.getApi().getControllers().get(controllerName);
        String methodPath = controller.getEndpoints().get(methodName);
        return controller.getPath() + methodPath;
    }
}
