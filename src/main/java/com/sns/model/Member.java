package com.sns.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class Member {
    private byte[] id;
    @NotEmpty(message = "ID를 입력해주세요.")
    @Size(min = 6, max = 20, message = "아이디는 6~20자 내로 작성해주세요.")
    private String user_id;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 내로 작성해주세요.")
    private String pw;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;
    private String profile_name;
    private String profile_text;
    private String profile_image;
    private Date created_at;
}
