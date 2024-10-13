package su.zhenya.me.security.core.provider;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import su.zhenya.me.security.core.access.AccountTokenDescriptor;
import su.zhenya.me.security.core.access.AccountTokenService;
import su.zhenya.me.security.core.provider.feign.AuthenticationServerFeignClient;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationProvider extends OncePerRequestFilter {

    private final AccountTokenService accountTokenService;
    private final AuthenticationServerFeignClient authenticationClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            securityDelegate(request, response, filterChain);
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    private void securityDelegate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws Exception {
        Optional<String> accessToken = extractAccessTokenFrom(request);

        accessToken
                .filter(authenticationClient::verifyAccountToken)
                .map(accountTokenService::getAccountToken)
                .ifPresent(accountToken -> {
                    AccountTokenDescriptor descriptor = accountTokenService.getAccountTokenDescriptor(accountToken.getTokenValue());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            descriptor, null,
                            descriptor.getAccountRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString())).toList()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractAccessTokenFrom(HttpServletRequest request) {
        final String authorizationScheme = "Bearer ";
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (nonNull(authorizationHeader) && !authorizationHeader.startsWith(authorizationScheme)) {
            throw new BadCredentialsException("Authorization header is incorrect");
        }

        if (nonNull(authorizationHeader)) {
            String accessToken = authorizationHeader.substring(authorizationScheme.length());

            if (isBlank(accessToken)) {
                throw new BadCredentialsException("Authorization accessToken is incorrect");
            }

            return Optional.of(accessToken);
        }

        return Optional.empty();
    }
}
