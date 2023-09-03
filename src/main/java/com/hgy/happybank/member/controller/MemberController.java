package com.hgy.happybank.member.controller;

import com.hgy.happybank.member.dto.MemberRegDTO;
import com.hgy.happybank.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/public/join")
    public ResponseEntity<?> registerMember(MemberRegDTO dto) {
        memberService.registerMember(dto);
        return ResponseEntity.ok("");
    }
}
