package edu.quaglia.calculatorrabbitconsumer.configs;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        String uniqueIdentifier = UUID.randomUUID().toString();

        MDC.put("UNIQUE_ID", uniqueIdentifier);

        if (!StringUtils.hasText(uniqueIdentifier)) {
            response.addHeader("Unique-Id", uniqueIdentifier);
        }

        chain.doFilter(request, response);

    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}