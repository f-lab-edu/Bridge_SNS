package com.sns.sns_bridge;

import com.sns.controller.MemberController;
import com.sns.exceptions.DuplicatedUserIdException;
import com.sns.exceptions.IncorrectPasswordException;
import com.sns.exceptions.UserIdNotFoundException;
import com.sns.model.Member;
import com.sns.model.MemberLoginInfo;
import com.sns.service.MemberService;
import com.sns.util.SessionKey;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;


@SpringBootTest
class SnsBridgeApplicationTests {
}

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    @Mock
    private HttpSession httpSession;

    private Member member;
    private MemberLoginInfo loginInfo;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .user_id("testID")
                .pw("testPW")
                .name("testUser")
                .email("testUser@email.com")
                .build();
        loginInfo = new MemberLoginInfo("testID", "testPW");
    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    public void addMember_success() {
        // 테스트에서 실제 테스트하지 않아도 되는 메소드 호출 부분을 doNothing 처리
        doNothing().when(memberService).checkDuplicatedUserID(member.getUser_id());
        doNothing().when(memberService).addMemberWithUUID(member);

        ResponseEntity<String> response = memberController.addMember(member);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("userID가 이미 존재하는 경우의 회원 가입 실패 테스트")
    public void addMember_userIdExists() {
        doThrow(new DuplicatedUserIdException()).when(memberService).checkDuplicatedUserID(member.getUser_id());

        assertThrows(DuplicatedUserIdException.class, () -> memberController.addMember(member));
    }

    @Test
    @DisplayName("중복된 userID가 존재할 경우의 API 테스트")
    public void checkDuplicatedMember_duplicated() {
        doThrow(DuplicatedUserIdException.class).when(memberService).checkDuplicatedUserID(member.getUser_id());

        assertThrows(DuplicatedUserIdException.class, () -> memberController.checkDuplicatedMember(member.getUser_id()));
    }

    @Test
    @DisplayName("중복된 userID가 존재하지 않을 경우의 API 테스트")
    public void checkDuplicatedMember_notDuplicated() {
        doNothing().when(memberService).checkDuplicatedUserID(member.getUser_id());

        ResponseEntity<String> response = memberController.checkDuplicatedMember(member.getUser_id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success() {
        when(memberService.getLoginUser(loginInfo)).thenReturn(member);

        ResponseEntity<String> response = memberController.loginMember(loginInfo, httpSession);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(httpSession).setAttribute(SessionKey.MEMBER, member);
    }

    @Test
    @DisplayName("로그인 실패 테스트 - ID 존재하지 않음")
    public void login_failed_id_not_found() {
        when(memberService.getLoginUser(loginInfo)).thenThrow(new UserIdNotFoundException());

        assertThrows(UserIdNotFoundException.class, () -> memberController.loginMember(loginInfo, httpSession));
        Mockito.verify(httpSession, Mockito.never()).setAttribute(SessionKey.MEMBER, member);
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 틀린 비밀번호")
    public void login_failed_incorrect_pw() {
        when(memberService.getLoginUser(loginInfo)).thenThrow(new IncorrectPasswordException());

        assertThrows(IncorrectPasswordException.class, () -> memberController.loginMember(loginInfo, httpSession));
        Mockito.verify(httpSession, Mockito.never()).setAttribute(SessionKey.MEMBER, member);
    }


    @Test
    @DisplayName("로그아웃 테스트")
    public void logout() {
        ResponseEntity<Void> response = memberController.logoutMember(httpSession);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(httpSession).invalidate();
    }
}
