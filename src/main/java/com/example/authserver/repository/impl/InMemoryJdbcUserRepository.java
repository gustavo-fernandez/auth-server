package com.example.authserver.repository.impl;

import com.example.authserver.repository.model.UserEntity;
import com.example.authserver.repository.spi.UserRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryJdbcUserRepository implements UserRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public boolean validateCredentials(String username, String password) {
    try {
      namedParameterJdbcTemplate.queryForObject("SELECT 1 FROM user WHERE username = :username AND password = :password",
        Map.of("username", username, "password", password),
        Integer.class);
      return true;
    } catch (IncorrectResultSizeDataAccessException e) { // query for object busca 1, pero se devolvio <> 1
      return false;
    }
  }

  @Override
  public UserEntity getUserByUsername(String username) {
    return namedParameterJdbcTemplate.queryForObject("SELECT id, username, password FROM user WHERE username = :username",
      Map.of("username", username),
      (rs, rowNum) -> UserEntity.builder()
        .id(rs.getLong("id"))
        .username(rs.getString("username"))
        .password(rs.getString("password"))
        .build());
  }
}
