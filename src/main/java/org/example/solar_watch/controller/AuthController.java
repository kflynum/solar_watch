package org.example.solar_watch.controller;

import lombok.RequiredArgsConstructor;
import org.example.solar_watch.model.AuthRequest;
import org.example.solar_watch.model.AuthResponse;
import org.example.solar_watch.model.User;
import org.example.solar_watch.service.JwtService;
import org.example.solar_watch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }
    @PostMapping("/register/{role}")
    public ResponseEntity<String> register(@PathVariable String role, @RequestBody AuthRequest request) {
        userService.register(request, "ROLE_" + role.toUpperCase());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    // Removed the role checks here as they are already managed in SecurityConfig

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(authentication.getName());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
