package com.refore.our.member.controller;

import com.refore.our.member.config.auth.CustomUserDetails;
import com.refore.our.member.dto.JwtDto;
import com.refore.our.member.jwt.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final TokenService tokenService;


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println("리프레쉬토큰");
        String refreshToken = request.getHeader("Refresh-Token");
        System.out.println("refreshToken = " + refreshToken);
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            System.out.println("여기온다고?");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token format.");
        }

        refreshToken = refreshToken.replace("Bearer ", "");
        JwtDto userInfoFromJwt = tokenService.getUserInfoFromJwt(refreshToken);
        Long userId = userInfoFromJwt.getId();
        String email = userInfoFromJwt.getEmail();
        System.out.println("email = " + email);
        if (userInfoFromJwt ==null && userInfoFromJwt.getId()==null) {
            System.out.println("여기는 안오겠지");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        }

        if (tokenService.validateRefreshToken(refreshToken, userId)) {
            System.out.println("여기토큰 검증끝");

            String newJwt = tokenService.createNewJwtForUserId(userId,email);
            System.out.println("newJwt = " + newJwt);
            System.out.println("userId = " + userId);
            System.out.println("email = " + email);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + newJwt)
                    .body("New JWT issued.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token.");
        }
    }

}
