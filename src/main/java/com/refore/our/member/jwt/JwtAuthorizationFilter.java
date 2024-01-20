package com.refore.our.member.jwt;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import com.refore.our.member.repository.MemberRepositoryImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

// BasicAuthenticationFilter 권한이나 인증이 필요한경우만 필터가 동작

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepositoryDataJpa memberRepositoryDataJpa;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepositoryDataJpa memberRepositoryDataJpa) {
        super(authenticationManager);
        this.memberRepositoryDataJpa=memberRepositoryDataJpa;

    }
    // 인증이나 권한 필요한경우 동작
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 요청");
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader = " + jwtHeader);
        
        
        // header 가 있는지 확인
        if(jwtHeader==null || !jwtHeader.startsWith("Bearer")){
            System.out.println("헤더없고");
            chain.doFilter(request,response);
            return;
        }
        // 토큰 검증후 정상적인 사용자인지 확인
        String jwt = request.getHeader("Authorization").replace("Bearer ","");
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key.getBytes()) // 문자열 키를 바이트 배열로 변환
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            Long userId = claims.get("id", Long.class);
            String userEmail = claims.get("email", String.class);
            System.out.println("userEmail = " + userEmail);
            System.out.println("userId = " + userId);

            if(userEmail!=null){
                Optional<JoinEntity> byId = memberRepositoryDataJpa.findById(userId);
                CustomUserDetails customUserDetails = new CustomUserDetails(byId.get());
                System.out.println("customUserDetails.getJoinEntity().getRole() = " + customUserDetails.getJoinEntity().getRole());
                //jwt 토큰을 통해서 정상이면 Authentication 객체를 만들어줌
                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,customUserDetails.getPassword(),customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);  // 시큐리티세션에 접근 Authentication 객체를 저장
                chain.doFilter(request,response);
            }

            // 추가적인 검증 로직 (예: 유저 이름을 통한 검증, 만료 시간 확인 등)
        } catch (Exception e) {
            // 파싱 또는 검증 실패시 처리
            System.out.println("e = " + e);
        }




    }
}
