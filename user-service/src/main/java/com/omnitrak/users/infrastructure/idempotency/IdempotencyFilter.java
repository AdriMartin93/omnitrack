package com.omnitrak.users.infrastructure.idempotency;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class IdempotencyFilter  extends OncePerRequestFilter {

    private static final String HEADER_NAME = "Idempotency-Key";
    private final RedisIdempotencyAdapter idempotencyAdapter;

    public IdempotencyFilter(RedisIdempotencyAdapter idempotencyAdapter) {
        this.idempotencyAdapter = idempotencyAdapter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        if (!"POST".equalsIgnoreCase(method) && !"PUT".equalsIgnoreCase(method) && !"PATCH".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        String idempotencyKey = request.getHeader(HEADER_NAME);

        if (!StringUtils.hasText(idempotencyKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> cachedResponse = idempotencyAdapter.getResponse(idempotencyKey);

        if (cachedResponse.isPresent()) {
            if ("PROCESSING".equals(cachedResponse.get())) {
                response.setStatus(HttpStatus.CONFLICT.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"error\": \"Petición en proceso. Intente de nuevo en unos segundos.\"}");
                return;
            }

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(cachedResponse.get());
            return;
        }

        if (!idempotencyAdapter.lockKey(idempotencyKey)) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\": \"Petición duplicada concurrente.\"}");
            return;
        }

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(request, responseWrapper);

            if (responseWrapper.getStatus() >= 200 && responseWrapper.getStatus() < 300) {
                byte[] content = responseWrapper.getContentAsByteArray();
                if (content.length > 0) {
                    String body = new String(content, StandardCharsets.UTF_8);
                    idempotencyAdapter.saveResponse(idempotencyKey, body);
                }
            }
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }
}
