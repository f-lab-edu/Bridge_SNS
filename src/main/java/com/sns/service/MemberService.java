package com.sns.service;

import com.sns.model.Member;
import com.sns.model.MemberLoginInfo;

public interface MemberService {

    void addMemberWithUUID(Member member);

    void checkDuplicatedUserID(String user_id);

    Member getLoginUser(MemberLoginInfo loginInfo);
    Member getMemberById(byte[] id);
    void updateMember(Member member);
}
