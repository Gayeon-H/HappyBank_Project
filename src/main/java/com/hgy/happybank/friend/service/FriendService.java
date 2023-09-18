package com.hgy.happybank.friend.service;

import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.friend.domain.Friend;
import com.hgy.happybank.friend.repository.FriendRepository;
import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.member.domain.dto.MemberDTO;
import com.hgy.happybank.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    public void requestFriend(String email, long id) {
        Member fromMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        if (fromMember.getId() == id) {
            throw new BizException(ErrorCode.FRIEND_REQUEST_ERROR, "본인에게 친구 신청은 할 수 없습니다.");
        }

        Member toMember = memberRepository.findById(id)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        if (friendRepository.existsByToMemberIdAndFromMemberId(toMember.getId(), fromMember.getId())
                || friendRepository.existsByToMemberIdAndFromMemberId(fromMember.getId(), toMember.getId())) {
            throw new BizException(ErrorCode.FRIEND_REQUEST_ERROR, "이미 친구 신청을 하거나 받았습니다.");
        }

        friendRepository.save(Friend.builder()
                .toMemberId(toMember.getId())
                .fromMemberId(fromMember.getId())
                .build());
    }

    public Page<MemberDTO> getFriendRequestList(String email) {
        Member toMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        return friendRepository.findByToMemberIdAndAreWeFriends(toMember.getId(), false
                        , PageRequest.of(0, 10))
                .map(request -> {
                    Member member = memberRepository.findById(request.getFromMemberId())
                            .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));
                    return MemberDTO.builder()
                            .id(member.getId())
                            .nickname(member.getNickname())
                            .build();
                });
    }

    public void acceptFriendRequest(String email, long memberId) {
        Member toMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        Friend friend = friendRepository.findByToMemberIdAndFromMemberIdAndAreWeFriends(toMember.getId()
                        , memberId, false)
                .orElseThrow(() -> new BizException(ErrorCode.FRIEND_REQUEST_ERROR, "친구 요청이 존재하지 않습니다."));

        friend.setAreWeFriends(true);
        friendRepository.save(friend);
    }

    public Page<MemberDTO> getFriendList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        return friendRepository
                .findByToMemberIdOrFromMemberIdAndAreWeFriends(member.getId(), member.getId()
                        , true, PageRequest.of(0, 10))
                .map(friend -> {
                    long friendId = Objects.equals(friend.getToMemberId(), member.getId()) ?
                            friend.getFromMemberId() : friend.getToMemberId();
                    Member friendMember = memberRepository.findById(friendId)
                            .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

                    return MemberDTO.builder()
                            .id(friendMember.getId())
                            .nickname(friendMember.getNickname())
                            .build();
                });
    }

}
