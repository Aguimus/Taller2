package org.example.taller2.controller;

import lombok.RequiredArgsConstructor;
import org.example.taller2.jwt.JwtService;
import org.example.taller2.model.Cliente;
import org.example.taller2.model.Role;
import org.example.taller2.repository.ClienteRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        System.out.println(request.getUsername());
        UserDetails cliente = clienteRepository.findByUsername(request.getUsername()).orElseThrow();
        System.out.println(cliente);
        String token = jwtService.getToken(cliente);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        Cliente cliente = Cliente.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
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
