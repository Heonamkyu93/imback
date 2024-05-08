package com.refore.our.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtDto {

    private Long id;
    private String email;
}
