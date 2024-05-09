package com.refore.our.menu.exceptionHandler;

import com.refore.our.menu.exception.MenuDeleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MenuException {


    public ResponseEntity<String> MenuDeleteException(MenuDeleteException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }


}
