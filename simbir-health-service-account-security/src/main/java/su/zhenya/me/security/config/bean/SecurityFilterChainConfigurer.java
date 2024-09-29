package su.zhenya.me.security.config.bean;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityFilterChainConfigurer {

    void configure(HttpSecurity http) throws Exception;
}
