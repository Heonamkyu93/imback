package com.refore.our.member.controller;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.dto.JoinDto;
import com.refore.our.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/member/test")
    public void test2(Authentication authentication){
        System.out.println("멤버");
        CustomUserDetails customUserDetails=(CustomUserDetails) authentication.getPrincipal();
        System.out.println("customUserDetails.getUsername() = " + customUserDetails.getUsername());
        System.out.println("customUserDetails.getJoinEntity().getRole() = " + customUserDetails.getJoinEntity().getRole());
    }
    @GetMapping("/admin/test")
    public void admin(Authentication authentication){
        System.out.println("admin");
        CustomUserDetails customUserDetails=(CustomUserDetails) authentication.getPrincipal();
        System.out.println("customUserDetails.getUsername() = " + customUserDetails.getUsername());
        System.out.println("customUserDetails.getJoinEntity().getRole() = " + customUserDetails.getJoinEntity().getRole());
        System.out.println("여기 어드민");
    }
}
