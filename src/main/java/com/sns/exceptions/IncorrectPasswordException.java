package com.sns.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    private static final String MESSAGE = "잘못된 비밀번호입니다. 비밀번호를 확인해주세요";

    public IncorrectPasswordException() { // 틀린 비밀번호 예외
        super(MESSAGE);
    }
}
