package su.zhenya.me.security.core.provider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;
import su.zhenya.me.security.core.provider.feign.AuthenticationServerFeignClient;

public class AuthenticationProvider extends OncePerRequestFilter {

    // TODO: возможно нужно будет удалить этот интерфейс
    private AuthenticationServerUri authenticationServerUri;
    private AuthenticationServerFeignClient authenticationClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO: реализовать аутентификацию
        // TODO: в данной реализации получается так, что сервисы должны ходить на account-service под одним секретом токена
        // т.к. выпускает сервис аккаунтов и без каких либо ограничений. возможно, надо пересмотреть, что-то с секретами
        // для всех сервисов, потому что сейчас они для всех сервисов есть, а по факту токен всегда подписан одним секретом
        // сервиса аккаунтов
        filterChain.doFilter(request, response);
    }
}
