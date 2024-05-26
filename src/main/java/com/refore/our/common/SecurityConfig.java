package com.refore.our.common;

import com.refore.our.member.exceptionHandler.CustomAccessDeniedHandler;
import com.refore.our.member.jwt.JwtAuthFilter;
import com.refore.our.member.jwt.JwtAuthorizationFilter;
import com.refore.our.member.jwt.TokenService;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import com.refore.our.member.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CorsConfig corsConfig;
    private final MemberRepositoryDataJpa memberRepositoryDataJpa;
    private final TokenService tokenService;
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManager.class);
//    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationManagerBuilder.getObject();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //     .addFilterBefore(new MyFilter(), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthFilter(authenticationManager(),tokenService))  // AuthenticationManager를 전달
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),memberRepositoryDataJpa,tokenService))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/out/*","/login","/refresh-token","/out/menu","/out/images/*").permitAll()
                        .requestMatchers("/in/*","/in/*/*").authenticated()
                        .requestMatchers("/member/*").hasAuthority("ROLE_MEMBER")
                        .requestMatchers("/admin/*").hasAuthority("ROLE_ADMIN")
                )
                .build();
    }


}
