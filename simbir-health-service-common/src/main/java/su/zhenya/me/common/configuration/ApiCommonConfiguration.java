package su.zhenya.me.common.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "service.rest-api")
public class ApiCommonConfiguration {

    @NotNull
    private Map<String, Controller> controllers;

    @Data
    public static class Controller {

        @NotEmpty
        private String path;
        @NotNull
        private Map<String, String> endpoints;
    }
}
