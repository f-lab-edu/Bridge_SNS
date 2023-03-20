package com.sns.mapper;

import com.sns.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void addMember(Member member);
    Boolean checkDuplicatedUserID(String user_id);
}
