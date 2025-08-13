package com.dh.notes.service.impl;

import com.dh.notes.dto.requests.AuthRequest;
import com.dh.notes.dto.requests.RegisterRequest;
import com.dh.notes.dto.responses.AuthResponse;
import com.dh.notes.model.User;
import com.dh.notes.repository.UserRepository;
import com.dh.notes.service.AuthService;
import com.dh.notes.service.auth.JwtService;
import com.dh.notes.util.Constans;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(Constans.INVALID_USER_NAME);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(Constans.INVALID_EMAIL);
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Constans.DEFAULT_ROLE)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(Constans.USER_REGISTERED_SUCCESS);
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(Constans.USER_NOT_FOUND));

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword())
        );
        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}