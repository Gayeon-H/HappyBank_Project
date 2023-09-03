package com.hgy.happybank.member.dto;

import com.hgy.happybank.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class MyUserDetails extends User {

    private String email;
    private String name;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MyUserDetails(Member member) {
        this(member.getEmail(), member.getPassword(), member.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toSet()));
        this.email = member.getEmail();
        this.name = member.getName();
    }
}
