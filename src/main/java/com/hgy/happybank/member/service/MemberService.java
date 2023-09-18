package com.hgy.happybank.member.service;

import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.member.domain.dto.MemberDTO;
import com.hgy.happybank.member.domain.dto.MemberJoinDTO;
import com.hgy.happybank.member.repository.MemberRepository;
import com.hgy.happybank.member.type.Role;
import com.hgy.happybank.util.JWTProvider;
import com.hgy.happybank.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JWTProvider jwtProvider;
    private final MemberRepository memberRepository;

    public void join(MemberJoinDTO dto) {
        memberRepository.findByEmail(dto.getEmail())
                .ifPresent(member -> {
                    throw new BizException(ErrorCode.EMAIL_DUPLICATED);
                });

        memberRepository.findByNickname(dto.getNickname())
                .ifPresent(member -> {
                    throw new BizException(ErrorCode.NICKNAME_DUPLICATED);
                });

        Member member = dto.toMember().addRoles(Role.ROLE_USER);

        if (dto.isAdmin()) {
            member.addRoles(Role.ROLE_ADMIN);
        }

        memberRepository.save(member);
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        if (!PasswordUtils.equalPassword(password, member.getPassword())) {
            throw new BizException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtProvider.createJwt(email);
    }

    public Page<MemberDTO> searchNickname(String nickname) {
        return memberRepository.findByNicknameContainingIgnoreCase(nickname,
                        PageRequest.of(0, 10, Sort.Direction.ASC, "nickname"))
                .map(member -> MemberDTO.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .build());
    }

}
