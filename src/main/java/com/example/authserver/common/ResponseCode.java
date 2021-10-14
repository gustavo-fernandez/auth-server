package com.example.authserver.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseCode {

  EXITO("A01", "Exito"),
  TOKEN_EXPIRADO("E01", "Token expirado"),
  TOKEN_SIN_PERMISOS("E02", "Token no cuenta con los permisos necesarios"),
  INVALID_CREDENTIALS("E03", "Credenciales incorrectas");

  private final String code;
  private final String message;

}
