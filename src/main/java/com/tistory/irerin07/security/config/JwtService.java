package com.tistory.irerin07.security.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * @author 민경수
 * @description jwt service
 * @since 2023.10.02
 **********************************************************************************************************************/
@Service
public class JwtService {

  private static final String SECRET_KEY =
    "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5NjIzNzMwNSwiaWF0IjoxNjk2MjM3MzA1fQ.mCpZnHMNzJAYELF794UqwvMc3fkngkBqkIosb-X66ZY";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
    return Jwts.builder()
      .setClaims(extractClaims)
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJwt(token).getBody();
  }

  private Key getSignInKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
  }

}
