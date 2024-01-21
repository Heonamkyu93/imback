package com.refore.our.member.jwt;

import com.refore.our.member.config.auth.CustomUserDetails;
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
        String jwtHeader = request.getHeader("Authorization");

        // header 가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        // 토큰 검증후 정상적인 사용자인지 확인
        String jwt = request.getHeader("Authorization").replace("Bearer ", "");
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        try {


            if (jwtIsExpired(jwt)) {
                String refreshTokenHeader = request.getHeader("Refresh-Token");
                if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
                    String refreshToken = refreshTokenHeader.replace("Bearer ", "");
                    Long userId = getUserIdFromJwt(jwt);

                    if (tokenService.validateRefreshToken(refreshToken, userId)) {
                        // 새로운 Access 토큰 생성 및 발급
                        String newJwt = tokenService.createNewJwtForUserId(userId);
                        response.addHeader("Authorization", "Bearer " + newJwt);
                    }
                }
            } else {


                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key.getBytes()) // 문자열 키를 바이트 배열로 변환
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                Long userId = claims.get("id", Long.class);
                String userEmail = claims.get("email", String.class);

                if (userEmail != null) {
                    Optional<JoinEntity> byId = memberRepositoryDataJpa.findById(userId);
                    CustomUserDetails customUserDetails = new CustomUserDetails(byId.get());
                    System.out.println("customUserDetails.getJoinEntity().getRole() = " + customUserDetails.getJoinEntity().getRole());
                    //jwt 토큰을 통해서 정상이면 Authentication 객체를 만들어줌
                    Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, customUserDetails.getPassword(), customUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);  // 시큐리티세션에 접근 Authentication 객체를 저장
                    chain.doFilter(request, response);
                }


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

    private Long getUserIdFromJwt(String jwtToken) {
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key.getBytes())
                    .build()
                    .parseClaimsJws(jwtToken);
            return Long.parseLong(claims.getBody().getSubject());
        } catch (Exception e) {
            throw new RuntimeException("JWT 토큰에서 사용자 ID 추출 실패", e);
        }
    }


}
