package com.sns.model;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@Builder
public class Member {
    private int id;
    @NonNull
    private String user_id;
    @NonNull
    private String pw;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private String profile_name;
    private String profile_text;
    private String profile_image;
    private Date created_at;
}
