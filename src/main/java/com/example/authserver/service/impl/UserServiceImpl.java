package com.example.authserver.service.impl;

import com.example.authserver.repository.model.UserEntity;
import com.example.authserver.repository.spi.RoleRepository;
import com.example.authserver.repository.spi.UserRepository;
import com.example.authserver.service.spi.JwtService;
import com.example.authserver.service.spi.UserService;
import com.example.authserver.service.spi.model.AuthenticationResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final JwtService jwtService;

  @Override
  public AuthenticationResponse doLogin(String username, String password) {
    boolean loginOk = userRepository.validateCredentials(username, password);
    if (!loginOk) {
      return AuthenticationResponse.builder()
        .loginSuccess(false)
        .build();
    }
    UserEntity userEntity = userRepository.getUserByUsername(username);
    List<String> roles = roleRepository.findRolesByUserId(userEntity.getId());
    String commaSeparatedRoles = roles.stream().collect(Collectors.joining(","));
    String token = jwtService.generateToken(username, commaSeparatedRoles);
    return AuthenticationResponse.builder()
      .loginSuccess(true)
      .token(token)
      .build();
  }
}
