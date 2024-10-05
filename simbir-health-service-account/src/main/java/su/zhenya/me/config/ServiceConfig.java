package su.zhenya.me.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "service.account")
public class ServiceConfig {

    @NotEmpty
    private String secret;
    @NotNull
    private Api api;

    @Data
    public static class Api {

        @NotNull
        private Map<String, Controller> controllers;
    }

    @Data
    public static class Controller {

        @NotEmpty
        private String path;
        @NotNull
        private Map<String, String> endpoints;
    }
}
