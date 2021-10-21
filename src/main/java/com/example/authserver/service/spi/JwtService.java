package com.example.authserver.service.spi;

public interface JwtService {

  String generateToken(String subject, String permissions);

  boolean validateToken(String token, String permission);

}

