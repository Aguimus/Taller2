package org.example.taller2.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.taller2.model.Cliente;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class JwtService {
    public String getToken(UserDetails cliente) {
        return getToken(new HashMap<>(), cliente);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails cliente) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(cliente.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode("SECRET_KEY");
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
