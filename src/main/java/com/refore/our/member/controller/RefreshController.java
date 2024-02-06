package com.refore.our.member.controller;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.dto.JwtDto;
import com.refore.our.member.jwt.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RefreshController {

    private final TokenService tokenService;


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String refreshToken = request.getHeader("Refresh-Token");
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token format.");
        }

        refreshToken = refreshToken.replace("Bearer ", "");
        JwtDto userInfoFromJwt = tokenService.getUserInfoFromJwt(refreshToken);
        Long userId = userInfoFromJwt.getId();
        String email = userInfoFromJwt.getEmail();
        if (userInfoFromJwt ==null && userInfoFromJwt.getId()==null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        }

        if (tokenService.validateRefreshToken(refreshToken, userId)) {
            log.info("refresh={}","새로운 jwt 발급완료");
            String newJwt = tokenService.createNewJwtForUserId(userId,email);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + newJwt)
                    .body("New JWT issued.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token.");
        }
    }

}
