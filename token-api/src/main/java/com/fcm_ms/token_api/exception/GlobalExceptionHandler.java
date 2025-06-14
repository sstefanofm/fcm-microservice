package com.fcm_ms.token_api.exception;

import java.util.Map;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fcm_ms.token_api.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handle404(
    NoResourceFoundException ex,
    HttpServletRequest request) {

    Map<String, String> errors = new HashMap<>();
    errors.put(ex.getResourcePath(), "The requested resource was not found");

    ErrorResponseDTO errorResponse = ErrorResponseDTO.of(
      HttpStatus.NOT_FOUND,
      "Endpoint not found",
      errors,
      request.getRequestURI()
    );

    return new ResponseEntity<>(
      errorResponse,
      errorResponse._getHttpStatus()
    );
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponseDTO> handleMethodNotSupported(
    HttpRequestMethodNotSupportedException ex,
    HttpServletRequest request) {

    Map<String, String> errors = new HashMap<>();
    errors.put(ex.getMethod(), "This method is not supported for this endpoint");

    ErrorResponseDTO errorResponse = ErrorResponseDTO.of(
      HttpStatus.METHOD_NOT_ALLOWED,
      "Method not allowed",
      errors,
      request.getRequestURI()
    );

    return new ResponseEntity<>(
      errorResponse,
      errorResponse._getHttpStatus()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleBodyValidationExceptions(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ErrorResponseDTO errorResponse = ErrorResponseDTO.of(
      HttpStatus.BAD_REQUEST,
      "Invalid fields",
      errors,
      request.getRequestURI()
    );

    return new ResponseEntity<>(
      errorResponse,
      errorResponse._getHttpStatus()
    );
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseDTO> handleEmptyRequestBody(
      HttpMessageNotReadableException ex,
      HttpServletRequest request) {

    Map<String, String> errors = new HashMap<>();
    errors.put("requestBody", "Request body is missing or malformed");

    ErrorResponseDTO errorResponse = ErrorResponseDTO.of(
      HttpStatus.BAD_REQUEST,
      "Empty or invalid request body",
      errors,
      request.getRequestURI()
    );

    return new ResponseEntity<>(
      errorResponse,
      errorResponse._getHttpStatus()
    );
  }
}
