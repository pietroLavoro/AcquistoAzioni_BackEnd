package com.tuorg.acquistoazioni.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public Map<String,Object> handleBusiness(BusinessException ex){
    return Map.of("error","BUSINESS_RULE","message",ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public Map<String,Object> handleNotFound(NotFoundException ex){
    return Map.of("error","NOT_FOUND","message",ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String,Object> handleValidation(MethodArgumentNotValidException ex){
    var errs = new ArrayList<>();
    ex.getBindingResult().getFieldErrors()
      .forEach(fe -> errs.add(Map.of("field", fe.getField(), "msg", fe.getDefaultMessage())));
    return Map.of("error","VALIDATION","details", errs);
  }
}
