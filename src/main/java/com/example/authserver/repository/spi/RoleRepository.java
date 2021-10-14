package com.example.authserver.repository.spi;

import java.util.List;

public interface RoleRepository {

  List<String> findRolesByUserId(Long userId);

}
