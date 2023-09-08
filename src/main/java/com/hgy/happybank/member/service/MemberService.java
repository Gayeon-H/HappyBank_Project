package com.hgy.happybank.member.service;

import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.member.domain.dto.MemberJoinDTO;
import com.hgy.happybank.member.repository.MemberRepository;
import com.hgy.happybank.member.type.Role;
import com.hgy.happybank.util.JWTUtils;
import com.hgy.happybank.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JWTUtils jwtUtils;
    private final MemberRepository memberRepository;

    public String join(MemberJoinDTO dto) {
        memberRepository.findByEmail(dto.getEmail())
                .ifPresent(member -> {
                    throw new BizException(ErrorCode.EMAIL_DUPLICATED);
                });

        memberRepository.findByNickname(dto.getNickname())
                .ifPresent(member -> {
                    throw new BizException(ErrorCode.NICKNAME_DUPLICATED);
                });

        memberRepository.save(dto.toMember().addRoles(Role.USER));
        return "SUCCESS";
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.EMAIL_NOT_FOUND));

        if (!PasswordUtils.equalPassword(password, member.getPassword())) {
            throw new BizException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtUtils.createJwt(email);
    }
}
