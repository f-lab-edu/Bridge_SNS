package com.sns.controller;

import com.sns.model.Member;
import com.sns.model.MemberLoginInfo;
import com.sns.service.MemberService;
import com.sns.util.SessionKey;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private static final ResponseEntity RESPONSE_OK = new ResponseEntity(HttpStatus.OK);

    @PostMapping("/signup")
    public ResponseEntity<String> addMember(@Valid @RequestBody Member member) {
        // User_id의 중복 체크
        memberService.checkDuplicatedUserID(member.getUser_id());

        // UUID를 id에 추가한 후, Member의 데이터를 DB에 추가
        memberService.addMemberWithUUID(member);

        return RESPONSE_OK;
    }

    @GetMapping("/isDuplicated")
    public ResponseEntity<String> checkDuplicatedMember(@RequestParam String user_id) {
        // User_id의 중복 체크
        memberService.checkDuplicatedUserID(user_id);

        return RESPONSE_OK;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginMember(@Valid @RequestBody MemberLoginInfo loginInfo, HttpSession session) {
        Member member = memberService.getLoginUser(loginInfo);

        session.setAttribute(SessionKey.MEMBER, member);
        return RESPONSE_OK;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutMember(HttpSession httpSession) {

        httpSession.invalidate();
        return RESPONSE_OK;
    }
}
