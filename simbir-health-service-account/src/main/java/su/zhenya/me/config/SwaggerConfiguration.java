package su.zhenya.me.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import su.zhenya.me.api.rest.controller.AccountController;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                             .group("v1")
                             .packagesToScan(AccountController.class.getPackageName())
                             .build();
    }
}
