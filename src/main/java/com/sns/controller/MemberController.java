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

import java.util.Optional;

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

    @PatchMapping("/account")
    public ResponseEntity<String> updateMember(@Valid @RequestBody Member updatedMember, HttpSession session) {
        Optional<byte[]> optionalMemberId = Optional.ofNullable((byte[]) session.getAttribute(SessionKey.MEMBER));
        if(optionalMemberId.isPresent()) {
            Member existingMember = memberService.getMemberById(optionalMemberId.get());

            existingMember.setUser_id(updatedMember.getUser_id());
            existingMember.setPw(updatedMember.getPw());
            existingMember.setName(updatedMember.getName());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setProfile_name(updatedMember.getProfile_name());
            existingMember.setProfile_text(updatedMember.getProfile_text());
            existingMember.setProfile_image(updatedMember.getProfile_image());

            memberService.updateMember(existingMember);

            return ResponseEntity.ok("회원 정보가 성공적으로 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }
}
