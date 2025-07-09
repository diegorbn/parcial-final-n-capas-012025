package com.uca.parcialfinalncapas.service;

import com.uca.parcialfinalncapas.dto.request.AuthRequest;
import com.uca.parcialfinalncapas.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}
