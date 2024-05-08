package com.refore.our.menu.controller;

import com.refore.our.menu.dto.MenuDto;
import com.refore.our.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;


    @PostMapping()
    public ResponseEntity<String> menuInsert(@RequestBody MenuDto menuDto){
        menuService.menuInsert(menuDto);

    }



    @GetMapping()
    public ResponseEntity<String> menuIndex(){
        menuService.menuIndex();
        return ResponseEntity.ok("테스트");
    }


    @PostMapping()
    public ResponseEntity<String> menuInsert(){

        return ResponseEntity.ok("등록");
    }




}
