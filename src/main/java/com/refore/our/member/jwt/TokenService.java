package com.refore.our.member.jwt;

// TokenService.java
import com.refore.our.member.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
@Service
public class TokenService {

    private RedisTemplate<String, String> redisTemplate;

    public TokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveRefreshToken(Long userId, String refreshToken) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set("refreshToken:" + userId, refreshToken, 7, TimeUnit.DAYS); // Refresh 토큰 7일간 유효
    }

    // Redis에서 Refresh 토큰 가져오기
    public String getRefreshToken(Long userId) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get("refreshToken:" + userId);
    }
    public boolean validateRefreshToken(String refreshToken, Long userId) {
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        try {
            String storedRefreshToken = redisTemplate.opsForValue().get("refreshToken:" + userId);
            if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
                Jws<Claims> claims = Jwts.parserBuilder()
                        .setSigningKey(key.getBytes())
                        .build()
                        .parseClaimsJws(refreshToken);
                return !claims.getBody().getExpiration().before(new Date());
            }
        } catch (Exception e) {
        }
        return false;
    }
    public String createNewJwtForUserId(Long userId,String email) {
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (6000 * 1)); // 10분 후 만료

        return Jwts.builder()
                .setSubject("access")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("id", userId)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS512, key.getBytes())
                .compact();
    }
    public String createNewRefreshForUserId(Long userId,String email) {
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (60000 * 60*24*7)); // 1주

        return Jwts.builder()
                .setSubject("refresh")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("id", userId)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS512, key.getBytes())
                .compact();
    }
    public JwtDto getUserInfoFromJwt(String jwtToken) {
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key.getBytes()) // 토큰을 검증하기 위한 키
                    .build()
                    .parseClaimsJws(jwtToken);
            Long id = claims.getBody().get("id", Long.class); // Long 타입의 id 클레임 값 가져오기
            String email = claims.getBody().get("email", String.class); // String 타입의 email 클레임 값 가져오기
           JwtDto jwtDto = JwtDto.builder()
                   .id(id)
                   .email(email)
                   .build();
            return jwtDto;
        } catch (Exception e) {
            throw new RuntimeException("JWT 토큰에서 사용자 ID 추출 실패", e);
        }
    }

}
