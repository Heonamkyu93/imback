package com.refore.our.member.controller;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.dto.JoinDto;
import com.refore.our.member.dto.UpdateDto;
import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    @PostMapping("/out/join")
    public ResponseEntity<String> join(@Validated @RequestBody JoinDto joinDto) {
        System.out.println("joinDto.getMemberEmail() = " + joinDto.getMemberEmail());
         memberService.memberJoin(joinDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PutMapping("/in/infoUpdate")
    public ResponseEntity<String> infoUpdate(@Validated @RequestBody UpdateDto updateDto) {
        memberService.infoUpdate(updateDto);
        return new ResponseEntity<>("정보수정완료",HttpStatus.OK);
    }

    @GetMapping("/out/emailDuplicatedCheck")
    public ResponseEntity<Map<String, String>> emailDuplicatedCheck(@RequestParam(name = "value") String memberEmail) {
        JoinDto joinDto = JoinDto.builder()
                .memberEmail(memberEmail)
                .build();
        memberService.duplicationCheck(joinDto);
        String msg = "가입 가능한 이메일 입니다.";

        Map<String, String> response = new HashMap<>();
        response.put("msg", msg);
        response.put("type", "email");
        System.out.println("이메일");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/out/phoneNumberDuplicatedCheck")
    public ResponseEntity<Map<String, String>> phoneNumberDuplicatedCheck(@RequestParam(name = "value") String phoneNumber) {
        JoinDto joinDto = JoinDto.builder()
                .memberEmail(phoneNumber)
                .build();
        memberService.duplicationCheck(joinDto);
        String msg= "가입 가능한 전화번호 입니다.";
        Map<String, String> response = new HashMap<>();
        response.put("msg", msg);
        response.put("type", "phoneNumber");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/out/nicknameDuplicatedCheck")
    public ResponseEntity<Map<String, String>> nicknameDuplicatedCheck(@RequestParam(name = "value") String nickname) {
        JoinDto joinDto = JoinDto.builder()
                .memberEmail(nickname)
                .build();
        memberService.duplicationCheck(joinDto);
        String msg= "가입 가능한 닉네임 입니다.";
        Map<String, String> response = new HashMap<>();
        response.put("msg", msg);
        response.put("type", "nickname");

        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<JoinDto> memberInfoFind(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        JoinEntity joinEntity = customUserDetails.getJoinEntity();
        JoinDto joinDto = modelMapper.map(joinEntity, JoinDto.class);
        joinDto.setMemberPassword("");
        return new ResponseEntity<>(joinDto, HttpStatus.OK);
    }

}
