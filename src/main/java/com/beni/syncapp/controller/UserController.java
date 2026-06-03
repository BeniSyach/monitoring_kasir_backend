package com.beni.syncapp.controller;

import com.beni.syncapp.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.beni.syncapp.entity.Role;
import com.beni.syncapp.entity.User;
import com.beni.syncapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserResponse> getAll() {

        return userRepository.findAll()
                .stream()
                .map(user -> {

                    UserResponse response =
                            new UserResponse();

                    response.setId(
                            user.getId()
                    );

                    response.setUsername(
                            user.getUsername()
                    );

                    response.setEmail(
                            user.getEmail()
                    );

                    response.setRole(
                            user.getRole()
                    );

                    return response;
                })
                .toList();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "role", authentication
                                .getAuthorities()
                                .stream()
                                .findFirst()
                                .map(auth -> auth.getAuthority())
                                .orElse("USER")
                )
        );
    }

    @GetMapping("/{id}")
    public User getById(
            @PathVariable Long id
    ) {

        return userRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping
    public User create(
            @RequestBody User request
    ) {

        request.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        return userRepository.save(request);
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody User request
    ) {

        User user = userRepository
                .findById(id)
                .orElseThrow();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null &&
                !request.getPassword().isEmpty()) {

            user.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()
                    )
            );
        }

        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id
    ) {

        userRepository.deleteById(id);

        return "User berhasil dihapus";
    }
}
