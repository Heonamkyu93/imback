package com.refore.our.member.service;

import com.refore.our.member.dto.JoinDto;
import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.exception.DuplicateValueException;
import com.refore.our.member.memberEnum.MemberRole;
import com.refore.our.member.repository.MemberRepositoryImpl;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryImpl memberRepositoryImpl;
    private final MemberRepositoryDataJpa memberRepositoryDataJpa;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public void memberJoin(JoinDto joinDto) {
        duplicationCheck(joinDto);
        JoinEntity joinEntity = modelMapper.map(joinDto, JoinEntity.class);
        joinEntity.setMemberPassword(passwordEncoder.encode(joinEntity.getMemberPassword()));
        joinEntity.setRole(MemberRole.ROLE_GUEST);
        memberRepositoryImpl.memberJoin(joinEntity);
        joinMailSend(joinEntity);
    }

    private void joinMailSend(JoinEntity joinEntity) {
    // 인증메일 구현
    }

    private void duplicationCheck(JoinDto joinDto) {
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

    public void test() {
       // JoinEntity member = memberRepositoryImpl.findMember("example@email.com");
        Optional<JoinEntity> byMemberEmail = memberRepositoryDataJpa.findByMemberEmail("example1@email.com");
        System.out.println("byMemberEmail.get().getMemberEmail() = " + byMemberEmail.get().getMemberEmail());
     //   System.out.println("member.getMemberEmail() = " + member.getMemberEmail());
    }
}


