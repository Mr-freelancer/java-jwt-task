package com.javatask.jwt.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = 1234567L;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final Algorithm algorithm = Algorithm.HMAC256("secret");

    @Value("${jwt.secret}")
    private String secret;

    private Map<String, Claim> getAllClaimsFromToken(String token) {
        return JWT.decode(token).getClaims();
    }

    public Claim getClaimFromToken(String token, String claimName){
        return JWT.decode(token).getClaim(claimName);
    }

    public String getUserNameFromToken(String token){
        return JWT.decode(token).getClaim("username").asString();
    }

    public Date getExpirationDateFromToken(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return JWT.create().withPayload(claims).withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).sign(algorithm);
    }

    public boolean validateToken(String jwtToken, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String username = getUserNameFromToken(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        final Date expiration = getExpirationDateFromToken(jwtToken);
        return expiration.before(new Date());
    }
}
