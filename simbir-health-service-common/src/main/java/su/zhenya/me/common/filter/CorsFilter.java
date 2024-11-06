package su.zhenya.me.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.*;

@Component
public class CorsFilter extends OncePerRequestFilter {

    private static final String ALL = "*";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ALL);
        response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, ALL);
        response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALL);
        filterChain.doFilter(request, response);
    }
}
