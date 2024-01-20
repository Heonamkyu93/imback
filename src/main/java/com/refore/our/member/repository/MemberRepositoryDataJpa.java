package com.refore.our.member.repository;

import com.refore.our.member.entity.JoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepositoryDataJpa extends JpaRepository<JoinEntity,Long> {
    Optional<JoinEntity> findByMemberEmail(String memberEmail);

}
