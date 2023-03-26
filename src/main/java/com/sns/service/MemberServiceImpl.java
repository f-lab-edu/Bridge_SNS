package com.sns.service;


import com.sns.exceptions.DuplicatedUserIdException;
import com.sns.exceptions.FailedToSignUpException;
import com.sns.mapper.MemberMapper;
import com.sns.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public void addMemberWithUUID(Member member) {
        byte[] uuidBytes;
        int isAdded;

        do {
            uuidBytes = generateUUID();
            member.builder().id(uuidBytes);
            try {
                isAdded = memberMapper.addMember(member);
            } catch (DataIntegrityViolationException e) {
                isAdded = 0; // UUID가 중복되어 회원 가입에 실패한 경우
            }
        } while (isAdded == 0); // 다시 위의 do 구문을 실행

        if (isAdded == 0) { // 만약 그럼에도 회원가입이 실패하면 FailedToSignUpException을 던짐
            throw new FailedToSignUpException();
        }
    }

    private byte[] generateUUID() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    @Override
    public void checkDuplicatedUserID(String user_id) {
        boolean isDuplicatedUserID = memberMapper.checkDuplicatedUserID(user_id);
        if (isDuplicatedUserID) {
            throw new DuplicatedUserIdException();
        }
    }
}
