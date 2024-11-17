package org.example.taller2.controller;

import lombok.RequiredArgsConstructor;
import org.example.taller2.jwt.JwtService;
import org.example.taller2.model.Cliente;
import org.example.taller2.model.Role;
import org.example.taller2.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        Cliente cliente = Cliente.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .nombre(request.getNombre())
                .telefono(request.getTelefono())
                .estadoCuenta(request.isEstadoCuenta())
                .role(Role.USER)
                .build();

        clienteRepository.save(cliente);

        return AuthResponse.builder()
                .token(jwtService.getToken(cliente))
                .build();
    }
}
