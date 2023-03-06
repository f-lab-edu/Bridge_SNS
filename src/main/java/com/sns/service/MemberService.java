package com.sns.service;

import com.sns.model.Member;

public interface MemberService {
    public void addMember(Member member);
    public int checkDuplicatedMember(String id);
}
