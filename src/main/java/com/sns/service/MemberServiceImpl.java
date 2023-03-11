package com.sns.service;


import com.sns.mapper.MemberMapper;
import com.sns.model.Member;
import lombok.RequiredArgsConstructor;
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
    public Boolean checkDuplicatedID(String id) {
        return memberMapper.checkDuplicatedID(id);
    }

    @Override
    public Boolean checkDuplicatedUser(String user_id) {
        return memberMapper.checkDuplicatedUser(user_id);
    }
}
