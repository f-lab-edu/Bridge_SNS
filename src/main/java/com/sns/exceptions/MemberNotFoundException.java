package com.sns.exceptions;

public class MemberNotFoundException extends RuntimeException {
    private static final String MESSAGE = "사용자 정보를 찾을 수 없습니다";

    public MemberNotFoundException() { // 틀린 비밀번호 예외
        super(MESSAGE);
    }
}
