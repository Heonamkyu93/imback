package com.refore.our.member.jwt;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.dto.JwtDto;
import com.refore.our.member.entity.JoinEntity;
import com.refore.our.member.repository.MemberRepositoryDataJpa;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

// BasicAuthenticationFilter 권한이나 인증이 필요한경우만 필터가 동작

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private TokenService tokenService;
    private MemberRepositoryDataJpa memberRepositoryDataJpa;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepositoryDataJpa memberRepositoryDataJpa, TokenService tokenService) {
        super(authenticationManager);
        this.memberRepositoryDataJpa = memberRepositoryDataJpa;
        this.tokenService = tokenService;
    }

    // 인증이나 권한 필요한경우 동작
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getRequestURI().startsWith("/out/")) {
            // "/out/*" 패턴에 해당하는 요청은 권한 검사를 수행하지 않고 허용합니다.
            chain.doFilter(request, response);
            return;
        }
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("권한필터동작");
        // header 가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            System.out.println("헤더에 없음");
            chain.doFilter(request, response);
            return;
        }
        // 토큰 검증후 정상적인 사용자인지 확인
        String jwt = request.getHeader("Authorization").replace("Bearer ", "");
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key.getBytes()) // 문자열 키를 바이트 배열로 변환
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                Long userId = claims.get("id", Long.class);
                String userEmail = claims.get("email", String.class);

                if (userEmail != null) {
                    System.out.println("정보있음");
                    Optional<JoinEntity> byId = memberRepositoryDataJpa.findById(userId);
                    CustomUserDetails customUserDetails = new CustomUserDetails(byId.get());
                    //jwt 토큰을 통해서 정상이면 Authentication 객체를 만들어줌
                    Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, customUserDetails.getPassword(), customUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);  // 시큐리티세션에 접근 Authentication 객체를 저장
                    System.out.println(customUserDetails.getAuthorities());
                    System.out.println(customUserDetails.getJoinEntity().getRole());
                    System.out.println("필터통과");
                    chain.doFilter(request, response);
                }




            // 추가적인 검증 로직 (예: 유저 이름을 통한 검증, 만료 시간 확인 등)
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT token");
            return;
        } catch (AuthenticationException e) {
            logger.error("Authentication failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            return;
        } catch (Exception e) {
            logger.error("JWT token parsing failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token");
            return;
        }


    }

    private boolean jwtIsExpired(String jwtToken) {
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key.getBytes())
                    .build()
                    .parseClaimsJws(jwtToken);
            return claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // 토큰이 만료되었다면 ExpiredJwtException 발생
        } catch (Exception e) {
            throw new RuntimeException("JWT 토큰 파싱 실패", e);
        }
    }




}
