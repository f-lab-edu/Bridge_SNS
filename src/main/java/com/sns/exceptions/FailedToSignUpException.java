package com.sns.exceptions;

public class FailedToSignUpException extends RuntimeException {
    private static final String MESSAGE = "회원 가입에 실패했습니다.";

    public FailedToSignUpException() { // 유저 ID 중복 예외
        super(MESSAGE);
    }
}
