package su.zhenya.me.security.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "service.authentication")
public class ServiceAuthenticationProperties {

    @NotNull
    private Secret secret;
    @NotNull
    private AuthorizationServer authorizationServer;

    @Data
    @Validated
    public static class Secret {

        @NotEmpty
        private String key;
    }

    @Data
    @Validated
    public static class AuthorizationServer {

        @NotEmpty
        private String serverHostname;
        @NotEmpty
        private String accessTokenVerifyPath;
        @NotEmpty
        private String accessTokenReleasePath;
        @NotEmpty
        private String accessTokenSuspendPath;
    }
}
