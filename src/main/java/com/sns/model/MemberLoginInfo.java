package com.sns.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginInfo {
    @NotEmpty(message = "ID를 입력해주세요.")
    private final String user_id;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private final String pw;
}
