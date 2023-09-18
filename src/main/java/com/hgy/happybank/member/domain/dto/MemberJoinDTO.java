package com.hgy.happybank.member.domain.dto;

import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.util.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class MemberJoinDTO {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private boolean isAdmin;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .password(PasswordUtils.encryptedPassword(password))
                .name(name)
                .nickname(nickname)
                .registerAt(LocalDateTime.now())
                .build();
    }
}
