package com.refore.our.member.config.auth;

import com.refore.our.member.entity.JoinEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {


    private final JoinEntity joinEntity;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> {
            return joinEntity.getRole().toString();
        });
        return authorities;
    }
    @Override
    public String getPassword() {
        return joinEntity.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return joinEntity.getMemberEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
