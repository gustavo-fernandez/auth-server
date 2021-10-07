package com.example.authserver.web.controller;

import com.example.authserver.common.ResponseCode;
import com.example.authserver.service.spi.JwtService;
import com.example.authserver.web.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    boolean isValid = jwtService.validateToken(token, action);
    return ApiResponse.<Boolean>builder()
      .code(ResponseCode.EXITO.getCode())
      .message(ResponseCode.EXITO.getMessage())
      .data(isValid).build();
  }

}
