package com.refore.our.member.jwt;

// TokenService.java
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
    public String createNewJwtForUserId(Long userId) {
        String key = "dkssudgktpdyakssktjqksrkqttmqslekgkgkghgh123testabcasdasdasdwseqasdasdasdasdasdasdasdsadassdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssseasda";
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (60000 * 10)); // 10분 후 만료

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, key.getBytes())
                .compact();
    }


}
