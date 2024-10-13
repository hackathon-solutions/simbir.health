package su.zhenya.me.security.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "authentication.server")
public class AuthenticationProperties {

    private String tokenVerifyPath;
    private String tokenReleasePath;
    private String tokenSuspendPath;
}
