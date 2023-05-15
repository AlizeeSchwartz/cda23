package edu.aschwartz.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {
    @Value("${jwt.secret}")
    String jwtSecret;
    public String generateJwt(MonUserDetails userDetails){
        Map<String, Object> donnees = new HashMap<>();
        donnees.put("firstname", userDetails.getUtilisateur().getFirstname());
        donnees.put("lastname", userDetails.getUtilisateur().getLastname());
        donnees.put("role", userDetails.getUtilisateur().getRole().getName());

        return Jwts.builder()
                .setClaims(donnees)
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Claims  getData(String jwt){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isTokenValid(String jwt){
        try {
            Claims donnees = getData(jwt);
        }catch (SignatureException e){
            return false;
        }
        return true;
    }
}
