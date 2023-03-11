package com.sns.controller;

import com.sns.model.Member;
import com.sns.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        boolean isDuplicatedUser = memberService.checkDuplicatedUser(member.getUser_id());

        if (isDuplicatedUser) { // User_id(Alias)의 중복 체크
            return ResponseEntity.badRequest().body("이미 존재하는 ID입니다.");
        } else {
            member.builder().id(generateUniqueId()); // 서버측에 저장될 실제 유저의 ID를 저장
            memberService.addMember(member);
            return ResponseEntity.ok("회원 가입을 완료했습니다.");
        }
    }

    private String generateUniqueId() { // 서버측에 저장할 Unique ID를 DB에서 겹치지 않을 때까지 재귀적으로 생성
        String uuid = UUID.randomUUID().toString();
        boolean isDuplicatedID = memberService.checkDuplicatedID(uuid);

        if (isDuplicatedID) {
            uuid = generateUniqueId();
        }
        return uuid;
    }

    @GetMapping("/isDuplicated")
    public ResponseEntity<String> checkDuplicatedMember(@RequestParam String user_id) {
        boolean isDuplicated = memberService.checkDuplicatedUser(user_id);

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
