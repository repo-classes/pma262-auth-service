package store.auth;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import store.account.AccountOut;

@Service
public class JwtService {

    @Value("${store.jwt.secretKey}")
    private String secretKey;    

    public String generate(AccountOut account, long duration) {

        Date now = new Date();
        return Jwts.builder()
            .header().and()
            .id(account.id())
            .issuer("Insper::PMA")
            .claims(Map.of("email", account.email()))
            .signWith(getKey())
            .subject(account.name())
            .notBefore(now)
            .expiration(new Date(now.getTime() + duration))
            .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public String getId(String jwt) {
        JwtParser parser = Jwts.parser().verifyWith(getKey()).build();
        try {
            // the parser checks the signature, exp and nbf: an expired, not yet
            // valid, tampered or malformed token throws a JwtException
            Claims claims = parser.parseSignedClaims(jwt).getPayload();
            return claims.getId();
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Invalid token: " + e.getMessage());
        }
    }

}
