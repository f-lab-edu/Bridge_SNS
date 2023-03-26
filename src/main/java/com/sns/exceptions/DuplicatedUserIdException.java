package com.sns.exceptions;

public class DuplicatedUserIdException extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 ID입니다.";

    public DuplicatedUserIdException() { // 유저 ID 중복 예외
        super(MESSAGE);
    }
}
