package com.refore.our.member.exception;

public class DuplicateValueException extends RuntimeException {
    public DuplicateValueException(String field, String value) {
        super(String.format("%s '%s'는 이미 사용 중입니다.", field, value));
    }
}