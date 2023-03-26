package com.sns.service;

import com.sns.model.Member;

public interface MemberService {

    void addMemberWithUUID(Member member);

    void checkDuplicatedUserID(String user_id);

}
