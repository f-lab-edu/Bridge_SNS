package com.sns.service;

import com.sns.model.Member;

public interface MemberService {
    public void addMember(Member member);

    public Boolean checkDuplicatedID(String id);
    public Boolean checkDuplicatedUser(String user_id);
}
