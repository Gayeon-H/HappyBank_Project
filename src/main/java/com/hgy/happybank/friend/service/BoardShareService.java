package com.hgy.happybank.friend.service;

import com.hgy.happybank.board.domain.Board;
import com.hgy.happybank.board.repository.BoardRepository;
import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.friend.domain.BoardShare;
import com.hgy.happybank.friend.domain.dto.BoardShareRequest;
import com.hgy.happybank.friend.domain.dto.BoardShareResponse;
import com.hgy.happybank.friend.repository.BoardShareRepository;
import com.hgy.happybank.friend.repository.FriendRepository;
import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardShareService {

    private final BoardShareRepository boardShareRepository;
    private final BoardRepository boardRepository;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public BoardShareResponse shareBoard(String email, long friendId, BoardShareRequest boardShareRequest) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        if (friendRepository.findByToMemberIdAndFromMemberIdAndAreWeFriends(
                member.getId(), friendId, true).isEmpty()
                && friendRepository.findByToMemberIdAndFromMemberIdAndAreWeFriends(
                friendId, member.getId(), true).isEmpty()) {
            throw new BizException(ErrorCode.NOT_FRIEND);
        }

        Board board = boardRepository.findById(boardShareRequest.getBoardId())
                .orElseThrow(() -> new BizException(ErrorCode.BOARD_NOT_FOUND));

        if (!Objects.equals(board.getMember().getId(), member.getId())) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_BOARD, "본인의 저금통만 공유가 가능합니다.");
        }

        boardShareRepository.save(BoardShare.builder()
                .boardId(board.getId())
                .friendId(friendId)
                .build());

        return BoardShareResponse.builder()
                .friendNickname(friend.getNickname())
                .boardName(board.getBoardName())
                .build();
    }
}
