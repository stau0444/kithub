package com.toyproject.kithub.advice;

import com.toyproject.kithub.exception.CannotCancelException;
import com.toyproject.kithub.exception.ExistMemberException;
import com.toyproject.kithub.exception.NotEnoughStockException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice{

    @ExceptionHandler(value = ExistMemberException.class)
    public ResponseEntity existMemberException(ExistMemberException eme){
        return ResponseEntity.status(400).body(eme.getMessage());
    }

    @ExceptionHandler(value = NotEnoughStockException.class)
    public ResponseEntity notEnoughStockException(NotEnoughStockException nese){
        return ResponseEntity.status(400).body(nese.getMessage());
    }

    @ExceptionHandler(value = CannotCancelException.class)
    public ResponseEntity CannotCancelException(CannotCancelException cce){
        return ResponseEntity.status(400).body(cce.getMessage());
    }
}
