package com.sns.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class Handler {
    private static final ResponseEntity<String> INTERNAL_SERVER_ERROR_RESPONSE = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> GenericExceptionHandler(Exception ex) {
        log.error("알 수 없는 오류가 발생했습니다: ", ex);
        return INTERNAL_SERVER_ERROR_RESPONSE;
    }

    @ExceptionHandler(DBConnectionException.class) // DB 연결 실패에 대한 예외 핸들러
    public ResponseEntity<String> handleDBConnectionException(DBConnectionException ex) {
        log.error("DB 연결에 실패했습니다: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
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

        log.warn("부정확하게 기입된 Member의 정보가 있습니다: \n{}", errors);
        return ResponseEntity.badRequest().body(errors.toString());
    }

    @ExceptionHandler(FailedToSignUpException.class) // Sign-Up 실패에 대한 예외 핸들러
    public ResponseEntity<String> FailedToSignUpExceptionHandler(FailedToSignUpException ex) {
        log.error("회원 가입에 실패했습니다: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedUserIdException.class) // UserID의 중복에 대한 예외 핸들러
    public ResponseEntity<String> handleUserAlreadyExistsException(DuplicatedUserIdException ex) {
        log.warn("User ID가 중복되었습니다: ", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserIdNotFoundException.class) // 로그인 실패에 대한 예외 핸들러
    public ResponseEntity<String> handleFailedToLogInException(UserIdNotFoundException ex) {
        log.warn("User ID를 찾을 수 없습니다: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IncorrectPasswordException.class) // 로그인 실패에 대한 예외 핸들러
    public ResponseEntity<String> handleFailedToLogInException(IncorrectPasswordException ex) {
        log.warn("올바르지 않은 비밀번호입니다: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MemberNotFoundException.class) // MemberNotFoundException에 대한 예외 핸들러
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException ex) {
        log.warn("멤버를 찾을 수 없습니다: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
