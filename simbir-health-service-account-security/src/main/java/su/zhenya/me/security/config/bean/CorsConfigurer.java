package su.zhenya.me.security.config.bean;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface CorsConfigurer extends Customizer<org.springframework.security.config.annotation.web.configurers.CorsConfigurer<HttpSecurity>> {

}
