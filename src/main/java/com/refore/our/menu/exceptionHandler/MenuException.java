package com.refore.our.menu.exceptionHandler;

import com.refore.our.menu.exception.MenuNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class MenuException {

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<String> MenuDeleteException(MenuNotFoundException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }


}
