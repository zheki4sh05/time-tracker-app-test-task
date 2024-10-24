package com.timetracker.timetrackerapptesttask.service;

import com.timetracker.timetrackerapptesttask.entity.*;
import org.springframework.security.core.authority.*;
import org.springframework.stereotype.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);

        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();



    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken( Map<String, Object> extraClaims, User userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = extractUserEmail(token);
        String emailAuthorities =((SimpleGrantedAuthority)userDetails.getAuthorities().toArray()[0]).getAuthority();
        boolean result = email.equals(emailAuthorities);
        return result && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        boolean r = extractExpiration(token).before(new Date());
        System.out.println(r);
        return r;
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


}
