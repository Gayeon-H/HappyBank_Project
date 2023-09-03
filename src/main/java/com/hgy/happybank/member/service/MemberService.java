package com.hgy.happybank.member.service;

import com.hgy.happybank.member.dto.MemberRegDTO;
import com.hgy.happybank.member.repository.MemberRepository;
import com.hgy.happybank.member.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void registerMember(MemberRegDTO dto) {
        memberRepository.save(dto.toMember().addRoles(Role.USER));
    }
}
