package com.sns.model;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@Builder
public class Member {
    @NonNull
    private String id;
    @NonNull
    private String user_id;
    @NonNull
    private String pw;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private String profile_name;
    private String profile_message;
    private String profile_image;
    private String profile_imagepath;
    private Date joindate;
}
