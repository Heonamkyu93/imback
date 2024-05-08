package com.refore.our.member.exception;

public class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(String message) {
            super(message);
        }
}
