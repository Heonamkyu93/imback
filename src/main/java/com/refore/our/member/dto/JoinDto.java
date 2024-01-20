package com.refore.our.member.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.refore.our.member.memberEnum.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {


    private Long memberId;

    @NotEmpty(message = "이메일 등록은 필수 입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    @JsonProperty("memberEmail")
    private String memberEmail;

    @NotEmpty(message = "이름 등록은 필수 입니다.")
    @JsonProperty("memberName")
    private String memberName;
    @JsonProperty("memberPassword")
    private String memberPassword;

    @NotEmpty(message = "전화번호 등록은 필수 입니다.")
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @NotEmpty(message = "전화번호 등록은 필수 입니다.")
    @JsonProperty("nickname")
    private String nickname;
    private MemberRole role;

}
