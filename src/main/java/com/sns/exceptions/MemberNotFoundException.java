package com.sns.exceptions;

public class MemberNotFoundException extends RuntimeException {
    private static final String MESSAGE = "사용자 정보를 찾을 수 없습니다";

    public MemberNotFoundException() { // 사용자 정보를 찾을 수 없는 예외
        super(MESSAGE);
    }
}
