package com.hgy.happybank.member.service;

import com.hgy.happybank.member.domain.dto.MyUserDetails;
import com.hgy.happybank.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", email);
        return new MyUserDetails(memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다.")));
    }
}
