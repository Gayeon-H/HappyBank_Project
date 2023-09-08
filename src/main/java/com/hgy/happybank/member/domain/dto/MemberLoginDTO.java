package com.hgy.happybank.member.domain.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
