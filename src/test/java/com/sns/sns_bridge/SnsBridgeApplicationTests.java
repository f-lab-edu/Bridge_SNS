package com.sns.sns_bridge;

import com.sns.config.MybatisConfig;
import com.sns.controller.MemberController;
import com.sns.model.Member;
import com.sns.service.MemberService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import java.sql.SQLException;

@SpringBootTest
class SnsBridgeApplicationTests {
}

@MybatisTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MybatisConfig.class)
@DisplayName("DB 커넥션 테스트")
class DBConnectionTest {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void testSqlSessionTemplate() {
        assertThat(sqlSessionTemplate).isNotNull();
        sqlSessionTemplate.getConnection();
    }
}

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .user_id("testID")
                .pw("testPW")
                .name("testUser")
                .email("testUser@email.com")
                .build();
    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    public void addMember_success() {
        // when()안의 메소드가 호출될 때, thenReturn(boolean)으로 메소드의 결과값을 가정할 수 있다
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(false);
        // 테스트에서 실제 테스트하지 않아도 되는 메소드 호출 부분을 doNothing 처리
        doNothing().when(memberService).addMember(member);

        ResponseEntity<String> response = memberController.addMember(member);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("회원 가입을 완료했습니다.", response.getBody());
    }

    @Test
    @DisplayName("userID가 이미 존재하는 경우의 회원 가입 실패 테스트")
    public void addMember_userIdExists() {
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(true);

        ResponseEntity<String> response = memberController.addMember(member);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("이미 존재하는 ID입니다.", response.getBody());
    }

    @Test
    @DisplayName("중복된 userID가 존재할 경우의 API 테스트")
    public void checkDuplicatedMember_duplicated() {
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(true);

        ResponseEntity<String> response = memberController.checkDuplicatedMember(member.getUser_id());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("이미 존재하는 ID입니다.", response.getBody());
    }

    @Test
    @DisplayName("중복된 userID가 존재하지 않을 경우의 API 테스트")
    public void checkDuplicatedMember_notDuplicated() {
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(false);

        ResponseEntity<String> response = memberController.checkDuplicatedMember(member.getUser_id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("사용 가능한 아이디입니다.", response.getBody());
    }
}
