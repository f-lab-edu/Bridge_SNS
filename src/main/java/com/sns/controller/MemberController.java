package com.sns.controller;

import com.sns.model.Member;
import com.sns.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        boolean isDuplicatedUser = memberService.checkDuplicatedUserID(member.getUser_id());

        if (isDuplicatedUser) { // User_id의 중복 체크
            return ResponseEntity.badRequest().body("이미 존재하는 ID입니다.");
        }

        memberService.addMember(member);
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
