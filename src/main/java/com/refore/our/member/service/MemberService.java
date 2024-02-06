package com.refore.our.member.service;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.dto.JoinDto;
import com.refore.our.member.dto.LoginDto;
import com.refore.our.member.dto.UpdateDto;
import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.exception.DuplicateValueException;
import com.refore.our.member.exception.PasswordMismatchException;
import com.refore.our.member.exception.UserNotFoundException;
import com.refore.our.member.memberEnum.MemberRole;
import com.refore.our.member.repository.MemberRepositoryImpl;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryImpl memberRepositoryImpl;
    private final MemberRepositoryDataJpa memberRepositoryDataJpa;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void memberJoin(JoinDto joinDto) {
        duplicationCheck(joinDto);
        JoinEntity joinEntity = modelMapper.map(joinDto, JoinEntity.class);
        joinEntity.setMemberPassword(bCryptPasswordEncoder.encode(joinEntity.getMemberPassword()));
        joinEntity.setRole(MemberRole.ROLE_GUEST);
      //  joinEntity.setRole(MemberRole.ROLE_ADMIN);
        memberRepositoryImpl.memberJoin(joinEntity);
        joinMailSend(joinEntity);
    }

    private void joinMailSend(JoinEntity joinEntity) {
    // 인증메일 구현
    }

    public void duplicationCheck(JoinDto joinDto) {
        JoinEntity member = memberRepositoryImpl.findMember(joinDto.getMemberEmail());
        if (member != null) {
            throw new DuplicateValueException("이메일", joinDto.getMemberEmail());
        }
        member = memberRepositoryImpl.findMember(joinDto.getPhoneNumber());
        if (member != null) {
            throw new DuplicateValueException("전화 번호", joinDto.getPhoneNumber());
        }
        member = memberRepositoryImpl.findMember(joinDto.getNickname());
        if (member != null) {
            throw new DuplicateValueException("닉네임", joinDto.getPhoneNumber());
        }
    }

    public void infoUpdate(UpdateDto updateDto) {
        memberRepositoryImpl.infoUpdate(updateDto);
    }

    @Transactional(readOnly = true)
    public boolean passwordConfirm(LoginDto loginDto) {
        Optional<JoinEntity> byId = memberRepositoryDataJpa.findById(loginDto.getMemberId());
        if(byId.isPresent()){
            boolean matches = bCryptPasswordEncoder.matches(loginDto.getMemberPassword(), byId.get().getMemberPassword());
            if(matches) return true;
            else throw new PasswordMismatchException();
        }else{
            throw new UserNotFoundException("정보를 찾을 수 없습니다.다시 로그인 하시기 바랍니다.");
        }

    }

    public void passwordChange(JoinDto joinDto) {
        joinDto.setMemberPassword(bCryptPasswordEncoder.encode(joinDto.getMemberPassword()));
        memberRepositoryImpl.passwordChange(joinDto);
    }
    @Transactional
    public void memberDelete(Long memberId) {
        memberRepositoryDataJpa.deleteById(memberId);
    }



}


