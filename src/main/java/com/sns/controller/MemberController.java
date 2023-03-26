package com.sns.controller;

import com.sns.model.Member;
import com.sns.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> addMember(@Valid @RequestBody Member member) {
        // User_id의 중복 체크
        memberService.checkDuplicatedUserID(member.getUser_id());

        // UUID를 id에 추가한 후, Member의 데이터를 DB에 추가
        memberService.addMemberWithUUID(member);

        return ResponseEntity.ok("회원 가입을 완료했습니다.");
    }

    @GetMapping("/isDuplicated")
    public ResponseEntity<String> checkDuplicatedMember(@RequestParam String user_id) {
        // User_id의 중복 체크
        memberService.checkDuplicatedUserID(user_id);

        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }
}
