package com.example.authserver.repository.impl;

import com.example.authserver.repository.spi.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryJdbcRoleRepository implements RoleRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<String> findRolesByUserId(Long userId) {
    return namedParameterJdbcTemplate.getJdbcTemplate().query(
      "SELECT name FROM roles WHERE user_id = ?",
      (rs, rowNum) -> rs.getString("name"),
      userId);
  }

}
