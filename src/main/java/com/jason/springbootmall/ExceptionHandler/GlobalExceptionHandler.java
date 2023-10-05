package com.jason.springbootmall.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSqlException(SQLIntegrityConstraintViolationException e){
        String errorMessage = "操作失敗，可能是因為重複的數據已存在。";

        return ResponseEntity.status(400).body(errorMessage);

    }
}
