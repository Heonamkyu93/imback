//package com.refore.our.common;
//
//import com.refore.our.member.jwt.JwtAuthFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//@RequiredArgsConstructor
//public class FilterDsl extends AbstractHttpConfigurer<FilterDsl, HttpSecurity> {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//        http
//
//                .addFilter(new JwtAuthFilter(authenticationManager));
//    }
//}
