package com.dh.notes.service;

import com.dh.notes.dto.requests.AuthRequest;
import com.dh.notes.dto.requests.RegisterRequest;
import com.dh.notes.dto.responses.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<?> register(RegisterRequest request);
    public ResponseEntity<AuthResponse> login(AuthRequest request);
}
