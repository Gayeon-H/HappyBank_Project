package com.hgy.happybank.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.member.domain.dto.MemberJoinDTO;
import com.hgy.happybank.member.domain.dto.MemberLoginDTO;
import com.hgy.happybank.member.repository.MemberRepository;
import com.hgy.happybank.member.service.MemberService;
import com.hgy.happybank.util.JWTProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    JWTProvider jwtProvider;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("회원가입 성공")
    void join() throws Exception {
        String email = "test";
        String password = "12fe34j4";
        String name = "홍길동";
        String nickname = "동에번쩍";

        mockMvc.perform(post("/api/v1/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsBytes(new MemberJoinDTO(email, password, name, nickname))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입 실패 - email 중복")
    void joinFail_email() throws Exception {
        String email = "test";
        String password = "12fe34j4";
        String name = "홍길동";
        String nickname = "동에번쩍";

        doThrow(new BizException(ErrorCode.EMAIL_DUPLICATED)).when(memberService).join(any());

        mockMvc.perform(post("/api/v1/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsBytes(new MemberJoinDTO(email, password, name, nickname))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    @DisplayName("회원가입 실패 - nickname 중복")
    void joinFail_nickname() throws Exception {
        String email = "test2";
        String password = "12fe34j4";
        String name = "홍길동";
        String nickname = "동에번쩍";

        doThrow(new BizException(ErrorCode.NICKNAME_DUPLICATED)).when(memberService).join(any());

        mockMvc.perform(post("/api/v1/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsBytes(new MemberJoinDTO(email, password, name, nickname))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        String email = "test2";
        String password = "12fe34j4";

        when(memberService.login(any(), any()))
                .thenReturn("token");


        mockMvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsBytes(new MemberLoginDTO(email, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - email 없음")
    void loginFail_email() throws Exception {
        String email = "test2";
        String password = "12fe34j4";

        when(memberService.login(any(), any()))
                .thenThrow(new BizException(ErrorCode.MEMBER_NOT_FOUND));


        mockMvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsBytes(new MemberLoginDTO(email, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - password 틀림")
    void loginFail_password() throws Exception {
        String email = "test2";
        String password = "12fe34j4";

        when(memberService.login(any(), any()))
                .thenThrow(new BizException(ErrorCode.INVALID_PASSWORD));


        mockMvc.perform(post("/api/v1/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsBytes(new MemberLoginDTO(email, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}