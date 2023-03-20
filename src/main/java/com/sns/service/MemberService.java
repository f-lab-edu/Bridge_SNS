package com.sns.service;

import com.sns.model.Member;
import org.springframework.context.annotation.Bean;

public interface MemberService {
    void addMember(Member member);
    Boolean checkDuplicatedUserID(String user_id);
}
