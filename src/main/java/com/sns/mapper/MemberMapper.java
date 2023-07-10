package com.sns.mapper;

import com.sns.model.Member;
import com.sns.model.MemberLoginInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    int addMember(Member member);
    Boolean checkDuplicatedUserID(String user_id);
    String getStoredPassword(String user_id);
    Member getMember(MemberLoginInfo loginInfo);
    Member getMemberById(byte[] id);
    void deleteMember(Member member);
}
