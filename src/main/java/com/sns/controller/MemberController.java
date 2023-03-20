package com.sns.controller;

import com.sns.model.Member;
import com.sns.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        boolean isDuplicatedUser = memberService.checkDuplicatedUserID(member.getUser_id());

        // User_id의 중복 체크
        if (isDuplicatedUser) {
            return ResponseEntity.badRequest().body("이미 존재하는 ID입니다.");
        }

        // ID를 UUID로 생성 후 Member의 id로 설정 후 DB에 추가 시도
        // DB에 중복된 UUID가 있으면 UUID 재생성 이후 중복되지 않을 때까지 같은 작업 반복
        boolean isDuplicatedUUID;
        byte[] uuidBytes;
        do {
            UUID uuid = UUID.randomUUID();
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
            // 상위 64비트를 putLong 메소드를 통해 현재 버퍼 위치에 저장하고, 버퍼 위치 8 증가
            byteBuffer.putLong(uuid.getMostSignificantBits());
            // 하위 64비트에 윗 줄과 같은 처리
            byteBuffer.putLong(uuid.getLeastSignificantBits());
            // 8+8 바이트 크기의 바이트 배열을 uuidBytes에 할당
            uuidBytes = byteBuffer.array();

            try {
                // 생성된 UUID를 member의 id로 설정
                member.builder().id(uuidBytes);
                memberService.addMember(member);
                isDuplicatedUUID = false;
            // UUID 중복 예외 처리, 컨트롤러가 특정 예외 유형에 의존하는 것을 방지하기 위해 여기 선언
            } catch (DataIntegrityViolationException e) {
                Throwable rootCause = e.getRootCause();
                if (rootCause instanceof SQLException) {
                    SQLException sqlException = (SQLException) rootCause;
                    // MySQL 중복 키 에러 코드(1062)에 대해서만 isDuplicatedUUID가 참을 반환하게 설정
                    if (sqlException.getErrorCode() == 1062) {
                        isDuplicatedUUID = true;
                    } else {
                        throw e;
                    }
                } else {
                    throw e;
                }
            }
        } while (isDuplicatedUUID);

        return ResponseEntity.ok("회원 가입을 완료했습니다.");
    }

    @GetMapping("/isDuplicated")
    public ResponseEntity<String> checkDuplicatedMember(@RequestParam String user_id) {
        boolean isDuplicated = memberService.checkDuplicatedUserID(user_id);

        String responseBody;
        HttpStatus httpStatus;
        if (isDuplicated) {
            responseBody = "이미 존재하는 ID입니다.";
            httpStatus = HttpStatus.CONFLICT;
        } else {
            responseBody = "사용 가능한 아이디입니다.";
            httpStatus = HttpStatus.OK;
        }

        return ResponseEntity.status(httpStatus).body(responseBody);
    }
}
