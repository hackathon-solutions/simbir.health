package su.zhenya.me.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// TODO: вынести в общий модуль
@Data
@Component
@ConfigurationProperties(prefix = "service.account")
public class ServiceConfiguration {

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
