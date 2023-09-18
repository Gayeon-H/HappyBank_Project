package com.hgy.happybank.member.controller;

import com.hgy.happybank.member.domain.dto.MemberJoinDTO;
import com.hgy.happybank.member.domain.dto.MemberLoginDTO;
import com.hgy.happybank.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinDTO dto) {
        memberService.join(dto);
        return ResponseEntity.ok("회원가입이 성공되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated MemberLoginDTO dto) {
        return ResponseEntity.ok().body(memberService.login(dto.getEmail(), dto.getPassword()));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchNickname(@RequestParam("nickname") String nickname) {

        return ResponseEntity.ok().body(memberService.searchNickname(nickname));
    }

}
