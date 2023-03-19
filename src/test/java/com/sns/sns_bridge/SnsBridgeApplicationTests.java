package com.sns.sns_bridge;

import com.sns.config.MybatisConfig;
import com.sns.controller.MemberController;
import com.sns.model.Member;
import com.sns.service.MemberService;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

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
    public void addMember_success() {
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(false);

        ResponseEntity<String> response = memberController.addMember(member);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("회원 가입을 완료했습니다.", response.getBody());
    }

    @Test
    public void addMember_userIdExists() {
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(true);

        ResponseEntity<String> response = memberController.addMember(member);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("이미 존재하는 ID입니다.", response.getBody());
    }

    @Test
    public void checkDuplicatedMember_duplicated() {
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(true);

        ResponseEntity<String> response = memberController.checkDuplicatedMember(member.getUser_id());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("이미 존재하는 ID입니다.", response.getBody());
    }

    @Test
    public void checkDuplicatedMember_notDuplicated() {
        when(memberService.checkDuplicatedUserID(member.getUser_id())).thenReturn(false);

        ResponseEntity<String> response = memberController.checkDuplicatedMember(member.getUser_id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("사용 가능한 아이디입니다.", response.getBody());
    }
}
