package com.sns.service;


import com.sns.mapper.MemberMapper;
import com.sns.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;

    @Override
    public void addMember(Member member) {
        memberMapper.addMember(member);
    }

    @Override
    public int checkDuplicatedMember(String id) {
        return memberMapper.checkDuplicatedMember(id);
    }
}
