package com.sns.service;

import com.sns.model.Member;
import org.springframework.context.annotation.Bean;

public interface MemberService {
    public void addMember(Member member);
    public Boolean checkDuplicatedUserID(String user_id);
}
