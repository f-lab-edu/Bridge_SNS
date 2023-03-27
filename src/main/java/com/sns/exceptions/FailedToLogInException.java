package com.sns.exceptions;

public class FailedToLogInException extends RuntimeException {
    private static final String MESSAGE = "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요";

    public FailedToLogInException() { // 로그인 실패 예외
        super(MESSAGE);
    }
}
