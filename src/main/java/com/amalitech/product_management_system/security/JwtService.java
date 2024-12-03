package com.amalitech.product_management_system.security;

import com.amalitech.product_management_system.user.UserTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserTokenRepository userTokenRepository;

    @Value("${application.secret_key}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(Map<String, Objects> claims, String userEmail) {
        return Jwts.builder()
                .subject(userEmail)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey())
                .compact();
    }

    public boolean isValidToken(String token) {
        boolean tokenPresent = userTokenRepository.findByToken(token).isPresent();
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.after(new Date()) && tokenPresent;
    }

    private SecretKey secretKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }
}
