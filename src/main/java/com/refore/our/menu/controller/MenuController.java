package com.refore.our.menu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {


    @GetMapping()
    public ResponseEntity<String> menuIndex(){

        return ResponseEntity.ok("테스트");
    }


    @PostMapping()
    public ResponseEntity<String> menuInsert(){

        return ResponseEntity.ok("등록");
    }




}
