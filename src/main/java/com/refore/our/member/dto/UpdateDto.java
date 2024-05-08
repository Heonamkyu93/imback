package com.refore.our.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.refore.our.member.memberEnum.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDto {


    @JsonProperty("memberId")
    private Long memberId;
    @Size(min = 2, max = 10)
    @NotEmpty(message = "이름 등록은 필수 입니다.")
    @JsonProperty("memberName")
    private String memberName;
    @Size(min = 10, max = 11)
    @NotEmpty(message = "전화번호 등록은 필수 입니다.")
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @Size(min = 2, max = 20)
    @NotEmpty(message = "별명 등록은 필수 입니다.")
    @JsonProperty("nickname")
    private String nickname;
}
