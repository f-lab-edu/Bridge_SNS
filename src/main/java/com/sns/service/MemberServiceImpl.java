package com.sns.service;


import com.sns.mapper.MemberMapper;
import com.sns.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public void addMember(Member member) {
        memberMapper.addMember(member);
    }

    @Override
    public Boolean checkDuplicatedUserID(String user_id) {
        return memberMapper.checkDuplicatedUserID(user_id);
    }
}
