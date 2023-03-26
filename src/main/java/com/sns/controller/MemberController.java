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
    public ResponseEntity<String> addMember(Member member) {
        memberService.addMember(member);
        return new ResponseEntity<>(null, null, HttpStatus.CREATED);
    }

    @GetMapping("/checkDuplicated")
    public ResponseEntity<String> checkDuplicatedMember(@RequestParam String id) {
        boolean isDuplicated = (memberService.checkDuplicatedMember(id) == 1);

        String responseBody;
        HttpStatus httpStatus;
        if (isDuplicated) {
            responseBody = "중복된 아이디";
            httpStatus = HttpStatus.CONFLICT;
        } else {
            responseBody = "사용 가능한 아이디";
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(responseBody, null, httpStatus);
    }
}
