package com.refore.our.member.controller;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.dto.JoinDto;
import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    @PostMapping("/join")
    public void join(@Validated @RequestBody JoinDto joinDto) {
        System.out.println("joinDto.getMemberEmail() = " + joinDto.getMemberEmail());
        /* memberService.memberJoin(joinDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(joinDto.getMemberEmail())
                .toUri();*/
      //  return ResponseEntity.created(location).build();
    }

    @PutMapping("/in/infoUpdate")
    public void infoUpdate(@Validated JoinDto joinDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        joinDto.setMemberId(customUserDetails.getJoinEntity().getMemberId());
        memberService.infoUpdate(joinDto);
    }

    @GetMapping("/emailDuplicatedCheck")
    public void emailDuplicatedCheck(JoinDto joinDto) {
        memberService.duplicationCheck(joinDto);
    }
    @GetMapping("/phoneNumberDuplicatedCheck")
    public void phoneNumberDuplicatedCheck(JoinDto joinDto) {
        memberService.duplicationCheck(joinDto);
    }
    @GetMapping("/nicknameDuplicatedCheck")
    public void nicknameDuplicatedCheck(JoinDto joinDto) {
        memberService.duplicationCheck(joinDto);
    }






    @PostMapping("/in/passwordConfirm")
    public boolean passwordConfirm(JoinDto joinDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        joinDto.setMemberId(customUserDetails.getJoinEntity().getMemberId());
        boolean result = memberService.passwordConfirm(joinDto);
        return result;
    }

    @PutMapping("/in/passwordChange")
    public void passwordChange(JoinDto joinDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        joinDto.setMemberId(customUserDetails.getJoinEntity().getMemberId());
        memberService.passwordChange(joinDto);
    }

    @DeleteMapping("/in/withdrawal")
    public void withdrawal(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        memberService.memberDelete(customUserDetails.getJoinEntity().getMemberId());
    }
    @GetMapping("/in/info")
    public JoinDto memberInfoFind(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        JoinEntity joinEntity = customUserDetails.getJoinEntity();
        JoinDto joinDto = modelMapper.map(joinEntity, JoinDto.class);
        return joinDto;
    }

}
