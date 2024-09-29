package su.zhenya.me.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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

        // TODO: придумать че нибудь нормальное сюда, чтобы контроллеры вытаскивались по имени, а не по индексу
        @NotNull
        private List<Controller> controllers;
    }

    @Data
    public static class Controller {

        @NotEmpty
        private String path;
        @NotNull
        private Map<String, String> endpoints;
    }
}
