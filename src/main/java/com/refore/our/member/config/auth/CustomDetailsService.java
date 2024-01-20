package com.refore.our.member.config.auth;

import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import com.refore.our.member.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomDetailsService implements UserDetailsService {

    private final MemberRepositoryDataJpa memberRepositoryDataJpa;
    private final MemberRepositoryImpl memberRepository;
    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        Optional<JoinEntity> byMemberEmail = memberRepositoryDataJpa.findByMemberEmail(memberEmail);
        return new CustomUserDetails(byMemberEmail.get());
    }
}
