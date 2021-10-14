package com.example.authserver.service.spi;

import com.example.authserver.service.spi.model.AuthenticationResponse;

public interface UserService {

  AuthenticationResponse doLogin(String username, String password);

}
