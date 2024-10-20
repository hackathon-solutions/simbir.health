package su.zhenya.me.common.security.core.provider;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import su.zhenya.me.common.security.core.access.AccountTokenDescriptor;
import su.zhenya.me.common.security.core.access.AccountTokenService;
import su.zhenya.me.common.security.core.provider.feign.AuthenticationServerFeignClient;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationProvider extends OncePerRequestFilter {

    private final AccountTokenService accountTokenService;
    private final AuthenticationServerFeignClient authenticationClient;
    private final AuthorizationContext authorizationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            extractAccessTokenFrom(request)
                    .filter(authenticationClient::verifyAccountToken)
                    .ifPresentOrElse(
                            accessToken -> {
                                AccountTokenDescriptor descriptor = accountTokenService.getAccountTokenDescriptor(accessToken);
                                Authorization authorization = new AuthorizationImpl(true, accessToken, descriptor);
                                authorizationContext.setAuthorization(authorization);
                            },
                            () -> {
                                Authorization authorization = new AuthorizationImpl(false, null, null);
                                authorizationContext.setAuthorization(authorization);
                            }
                    );
        } catch (TokenCringeException e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractAccessTokenFrom(HttpServletRequest request) throws TokenCringeException {
        final String authorizationScheme = "Bearer ";
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (nonNull(authorizationHeader) && !authorizationHeader.startsWith(authorizationScheme)) {
            throw new TokenCringeException("Authorization header is incorrect");
        }

        if (nonNull(authorizationHeader)) {
            String accessToken = authorizationHeader.substring(authorizationScheme.length());

            if (isBlank(accessToken)) {
                throw new TokenCringeException("Authorization accessToken is incorrect");
            }

            return Optional.of(accessToken);
        }

        return Optional.empty();
    }

    private static class TokenCringeException extends Exception {

        public TokenCringeException(String message) {
            super(message);
        }
    }
}
