package com.example.authserver.web.exceptionhandler;

import com.example.authserver.common.ResponseCode;
import com.example.authserver.web.model.ApiResponse;
import com.example.authserver.web.model.FieldValidationError;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class WebControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidations(MethodArgumentNotValidException validationException) {

    List<FieldValidationError> fieldValidationErrors = validationException.getBindingResult().getFieldErrors()
      .stream()
      .map(fieldError -> new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
      .collect(Collectors.toList());

    ApiResponse<?> apiResponse = ApiResponse.builder()
      .code("E10")
      .message("Error de validación")
      .fieldErrors(fieldValidationErrors)
      .build();
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ApiResponse<Boolean>> handleExpiredJwtException(ExpiredJwtException expiredJwtException) {
    ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
      .code(ResponseCode.TOKEN_EXPIRADO.getCode())
      .message(ResponseCode.TOKEN_EXPIRADO.getMessage())
      .data(false)
      .build();
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @ExceptionHandler(MissingRequestValueException.class)
  public ResponseEntity<ApiResponse<?>> handleMissingField(MissingRequestValueException missingRequestValueException) {
    ApiResponse<?> apiResponse = ApiResponse.builder()
      .code("E10")
      .message(missingRequestValueException.getMessage())
      .build();
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ApiResponse<?>> handleUnhandledExceptions(Throwable throwable) {
    log.error("Ocurrio un error no controlado", throwable);
    ApiResponse<?> apiResponse = ApiResponse.builder()
      .code("E99")
      .message("Ocurrio un error inesperado")
      .build();
    return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
