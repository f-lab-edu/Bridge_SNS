package com.sns.exceptions;

public class UserIdNotFoundException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 사용자 ID입니다";

    public UserIdNotFoundException() { // 존재하지 않는 사용자 ID 예외
        super(MESSAGE);
    }
}
