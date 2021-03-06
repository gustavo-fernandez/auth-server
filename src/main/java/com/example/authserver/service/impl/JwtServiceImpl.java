package com.example.authserver.service.impl;

import com.example.authserver.service.spi.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  private static final long JWT_DURATION = TimeUnit.HOURS.toMillis(2);
  private static final String JWT_SIGNATURE_SECRET = "secret";
  private static final String CLAIM_PERMISSIONS = "permissions";

  @Override
  public String generateToken(String subject, String permissions) {
    Date currentDate = new Date();
    long endTime = currentDate.getTime() + JWT_DURATION;
    String jwt = Jwts.builder()
      .setSubject(subject.toUpperCase())
      .setIssuedAt(currentDate)
      .setExpiration(new Date(endTime))
      .signWith(SignatureAlgorithm.HS256, JWT_SIGNATURE_SECRET)
      .claim(CLAIM_PERMISSIONS, permissions)
      .compact();
    return jwt;
  }

  @Override
  public boolean  validateToken(String token, String permissionToValidate) {
    Jws<Claims> parsedJwt = Jwts.parser()
      .setSigningKey(JWT_SIGNATURE_SECRET)
      .parseClaimsJws(token);
    String permissionsString = parsedJwt.getBody().get(CLAIM_PERMISSIONS, String.class);
    return Arrays.stream(permissionsString.split(","))
      .anyMatch(claimPermission -> permissionToValidate.equals(claimPermission));
  }
}
