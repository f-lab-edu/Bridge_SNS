package com.sns.exceptions;

public class DBConnectionException extends RuntimeException {
    private static final String MESSAGE = "회원 정보에 접근할 수 없습니다.";

    public DBConnectionException() { // DB 접속 실패 예외
        super(MESSAGE);
    }
}
