package com.beni.syncapp.security;

import com.beni.syncapp.service.CustomUserDetailsService;
import com.beni.syncapp.service.TokenBlacklistService;
import com.beni.syncapp.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = null;

        // ========================================
        // Ambil JWT dari Cookie
        // ========================================
        if (request.getCookies() != null) {

            for (Cookie cookie : request.getCookies()) {

                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // ========================================
        // Kalau token tidak ada
        // ========================================
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // ========================================
        // Validasi token
        // ========================================
        if (!jwtUtil.validateToken(token)
                || tokenBlacklistService.isBlacklisted(token)) {

            filterChain.doFilter(request, response);
            return;
        }

        // ========================================
        // Extract username dari JWT
        // ========================================
        String username = jwtUtil.extractUsername(token);

        // ========================================
        // Set Authentication ke SecurityContext
        // ========================================
        if (username != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);

        }

        filterChain.doFilter(request, response);
    }
}