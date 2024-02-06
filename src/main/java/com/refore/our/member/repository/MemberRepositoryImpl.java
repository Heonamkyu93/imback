package com.refore.our.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.refore.our.member.dto.ChangePwdDto;
import com.refore.our.member.dto.JoinDto;
import com.refore.our.member.dto.LoginDto;
import com.refore.our.member.dto.UpdateDto;
import com.refore.our.member.entity.JoinEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.*;
import java.util.List;
import static com.refore.our.member.entity.QJoinEntity.joinEntity;

@Repository
public class MemberRepositoryImpl {


    private final EntityManager em;
    private final JPAQueryFactory query;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Transactional
    public void memberJoin(JoinEntity joinEntity) {
        em.persist(joinEntity);
    }

    @Transactional(readOnly = true)
    public JoinEntity findMember(String value) {
        List<JoinEntity> memberEntities = query
                .select(joinEntity)
                .from(joinEntity)
                .where(
                        equalsCondition(joinEntity.memberEmail, value)
                                .or(equalsCondition(joinEntity.phoneNumber, value))
                                .or(equalsCondition(joinEntity.nickname, value))
                )
                .fetch();
        return memberEntities.isEmpty() ? null : memberEntities.get(0);
    }

    private <T> BooleanExpression equalsCondition(StringPath path, T value) {
        if (value != null && StringUtils.hasText(value.toString())) {
            return path.eq(value.toString());
        }
        return Expressions.FALSE;
    }
    @Transactional
    public void infoUpdate(UpdateDto updateDto) {   // 회원정보 업데이트시 유효성체크를 클라이언트에서 한 번 서버 컨트롤러에서 Validated 하기 때문에 따로 하지 않음
        JoinEntity joinEntity1 = em.find(JoinEntity.class, updateDto.getMemberId());
        joinEntity1.setMemberName(updateDto.getMemberName());
        joinEntity1.setNickname(updateDto.getNickname());
        joinEntity1.setPhoneNumber(updateDto.getPhoneNumber());
    }
    @Transactional
    public void passwordChange(ChangePwdDto changePwdDto) {
        JoinEntity joinEntity = em.find(JoinEntity.class, changePwdDto.getMemberId());
        joinEntity.setMemberPassword(changePwdDto.getMemberPassword());
    }
}
