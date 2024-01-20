package com.refore.our.member.controller;

import com.refore.our.member.dto.JoinDto;
import com.refore.our.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public void login(){

    }
    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody JoinDto joinDto) {
        memberService.memberJoin(joinDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(joinDto.getMemberEmail())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @PostMapping("/test")
    public void test(){
        memberService.test();
    }


}
