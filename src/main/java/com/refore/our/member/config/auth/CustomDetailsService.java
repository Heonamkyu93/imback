package com.refore.our.member.config.auth;

import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.exception.UserNotFoundException;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import com.refore.our.member.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomDetailsService implements UserDetailsService {

    private final MemberRepositoryDataJpa memberRepositoryDataJpa;
    private final MemberRepositoryImpl memberRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        JoinEntity joinEntity = memberRepositoryDataJpa.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new UserNotFoundException("이메일,비밀번호를 다시 확인하세요."));
        joinEntity.setLastLoginDate(LocalDate.now());
        return new CustomUserDetails(joinEntity);
    }
}
