package com.example.authserver.web.controller;

import com.example.authserver.common.ResponseCode;
import com.example.authserver.service.spi.JwtService;
import com.example.authserver.service.spi.UserService;
import com.example.authserver.service.spi.model.AuthenticationResponse;
import com.example.authserver.web.model.ApiResponse;
import com.example.authserver.web.model.UserData;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

  private final JwtService jwtService;
  private final UserService userService;

  @GetMapping("generate-token")
  public ApiResponse<String> generateToken(@Length(min = 2) @RequestParam String permissions) {
    String jwt = jwtService.generateToken(permissions);
    return ApiResponse.<String>builder()
      .code(ResponseCode.EXITO.getCode())
      .message(ResponseCode.EXITO.getMessage())
      .data(jwt).build();
  }

  @PostMapping("validate-token")
  public ApiResponse<Boolean> validateToken(
    @RequestHeader String token, @RequestParam String action) {
    boolean hasPermissions = jwtService.validateToken(token, action);
    ResponseCode responseCode = hasPermissions ? ResponseCode.EXITO : ResponseCode.TOKEN_SIN_PERMISOS;
    return ApiResponse.<Boolean>builder()
      .code(responseCode.getCode())
      .message(responseCode.getMessage())
      .data(hasPermissions)
      .build();
  }

  @PostMapping("")
  public ApiResponse<String> authentication(@Valid @RequestBody UserData userData) {
    AuthenticationResponse authenticationResponse = userService.doLogin(userData.getUsername(), userData.getPassword());
    if (authenticationResponse.isLoginSuccess()) {
      return ApiResponse.<String>builder()
        .code(ResponseCode.EXITO.getCode())
        .message(ResponseCode.EXITO.getMessage())
        .data(authenticationResponse.getToken())
        .build();
    } else {
      return ApiResponse.<String>builder()
        .code(ResponseCode.INVALID_CREDENTIALS.getCode())
        .message(ResponseCode.INVALID_CREDENTIALS.getMessage())
        .build();
    }
  }

}
