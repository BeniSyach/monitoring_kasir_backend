package com.beni.syncapp.controller;

import com.beni.syncapp.dto.*;
import com.beni.syncapp.entity.*;
import com.beni.syncapp.repository.UserRepository;
import com.beni.syncapp.service.TokenBlacklistService;
import com.beni.syncapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request
    ) {

        if (userRepository.existsByUsername(
                request.getUsername()
        )) {

            return "Username sudah digunakan";
        }

        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            return "Email sudah digunakan";
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return "Register berhasil";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token =
                jwtUtil.generateToken(
                        request.getUsername()
                );

        Cookie cookie = new Cookie(
                "token",
                token
        );

        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true kalau production HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login berhasil"
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletResponse response
    ) {

        Cookie cookie =
                new Cookie("token", null);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Logout berhasil"
                )
        );
    }
}
