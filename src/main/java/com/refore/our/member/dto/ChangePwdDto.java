package com.refore.our.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePwdDto {


    @JsonProperty("memberId")
    private Long memberId;
    @JsonProperty("memberPassword")
    private String memberPassword;
    @JsonProperty("confirmPwd")
    private String confirmPwd;
}
