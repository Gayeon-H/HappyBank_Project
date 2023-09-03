package com.hgy.happybank.member.dto;

import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.util.PasswordUtils;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class MemberRegDTO {

    private String email;
    private String password;
    private String name;
    private String nickName;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .password(PasswordUtils.encryptedPassword(password))
                .name(name)
                .nickname(nickName)
                .registerAt(LocalDateTime.now())
                .build();
    }
}
