package com.sns.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> GenericExceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // 잘못된 Member 정보 기입에 대한 예외 핸들러
    public ResponseEntity<String> ValidationExceptionsHandler(MethodArgumentNotValidException ex) {
        // 에러 메시지를 저장할 StringBuilder 생성
        StringBuilder errors = new StringBuilder();
        // 발생한 모든 에러를 가져와 리스트에 저장
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        // 에러 목록의 각 에러에 대해 에러 메세지 처리
        for (ObjectError error : allErrors) {
            errors.append(error.getDefaultMessage());
            errors.append("\n");
        }

        return ResponseEntity.badRequest().body(errors.toString());
    }

    @ExceptionHandler(FailedToSignUpException.class) // Sign-Up 실패에 대한 예외 핸들러
    public ResponseEntity<String> FailedToSignUpExceptionHandler(FailedToSignUpException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedUserIdException.class) // UserID에 중복에 대한 예외 핸들러
    public ResponseEntity<String> handleUserAlreadyExistsException(DuplicatedUserIdException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
