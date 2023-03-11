package com.sns.sns_bridge;

import com.sns.config.MybatisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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




