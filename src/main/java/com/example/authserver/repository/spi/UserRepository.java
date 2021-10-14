package com.example.authserver.repository.spi;


import com.example.authserver.repository.model.UserEntity;

public interface UserRepository {

  boolean validateCredentials(String username, String password);

  UserEntity getUserByUsername(String username);
}
