package com.refore.our.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDto {
    @JsonProperty("memberId")
    private Long memberId;
    @JsonProperty("memberEmail")
    private String memberEmail;
    @JsonProperty("memberPassword")
    private String memberPassword;


}
