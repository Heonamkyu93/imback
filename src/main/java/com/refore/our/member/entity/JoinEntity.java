package com.refore.our.member.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.refore.our.member.memberEnum.MemberRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "member_email")
    private String memberEmail;
    private String memberName;
    private String memberPassword;
    private String phoneNumber;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
