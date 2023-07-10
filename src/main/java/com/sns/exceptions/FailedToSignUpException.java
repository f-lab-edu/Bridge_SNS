package com.sns.exceptions;

public class FailedToSignUpException extends RuntimeException {
    private static final String MESSAGE = "회원 가입에 실패했습니다.";

    public FailedToSignUpException() { // 회원 가입 실패 예외
        super(MESSAGE);
    }
}
