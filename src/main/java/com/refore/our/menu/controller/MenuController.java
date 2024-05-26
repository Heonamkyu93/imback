package com.refore.our.menu.controller;

import com.refore.our.menu.dto.MenuDto;
import com.refore.our.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final MenuService menuService;


    @PostMapping("/in/menuInsert")
    public ResponseEntity<String> menuInsert(@ModelAttribute MenuDto menuDto){
        menuService.menuInsert(menuDto);
        return ResponseEntity.ok("등록");
    }



    @GetMapping("/out/menu")
    public ResponseEntity<Page<MenuDto>> menuIndex(@PageableDefault(page = 0, size = 9, sort = "buggerPrice", direction = Sort.Direction.DESC)Pageable pageable){
        Page<MenuDto> menuPage=menuService.menuIndex(pageable);
        return new ResponseEntity<>(menuPage, HttpStatus.OK);
    }



    @DeleteMapping()
    public ResponseEntity<String> menuDelete(@RequestBody MenuDto menuDto){
        menuService.menuDelete(menuDto);
        return ResponseEntity.ok("삭제");
    }

    @PutMapping()
    public ResponseEntity<String> menuUpdate(@RequestBody MenuDto menuDto){
        menuService.menuUpdate(menuDto);
        return ResponseEntity.ok("업데이트");
    }


}
