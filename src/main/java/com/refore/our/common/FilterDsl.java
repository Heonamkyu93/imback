/*
package com.refore.our.common;

import com.refore.our.member.jwt.JwtAuthFilter;
import com.refore.our.member.jwt.JwtAuthorizationFilter;
import com.refore.our.member.jwt.TokenService;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
@RequiredArgsConstructor
public class FilterDsl extends AbstractHttpConfigurer<FilterDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthorizationFilter(authenticationManager,memberRepositoryDataJpa,tokenService))
                .addFilter(new JwtAuthFilter(authenticationManager,tokenService)) ; // AuthenticationManager를 전달
    }
}
*/
