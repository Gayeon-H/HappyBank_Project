package com.hgy.happybank.board.service;

import com.hgy.happybank.board.domain.Board;
import com.hgy.happybank.board.domain.dto.BoardRegDTO;
import com.hgy.happybank.board.domain.dto.BoardResponse;
import com.hgy.happybank.board.domain.dto.BoardUpdateDTO;
import com.hgy.happybank.board.repository.BoardRepository;
import com.hgy.happybank.board.type.OpenDate;
import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final int MAX_BOARD = 3;

    public void register(BoardRegDTO boardRegDTO, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.EMAIL_NOT_FOUND));
        if (boardRepository.countByMember(member) >= MAX_BOARD) {
            throw new BizException(ErrorCode.OVER_REGISTERED_BOARD);
        }

        boardRepository.save(Board.builder()
                .boardName(boardRegDTO.getName())
                .registerAt(LocalDateTime.now())
                .openDate(getOpenDate(boardRegDTO))
                .member(member)
                .build());
    }

    private static LocalDate getOpenDate(BoardRegDTO boardRegDTO) {
        if (boardRegDTO.getOpenDate() == OpenDate.YEAR_END) {
            return LocalDate.of(LocalDate.now().getYear(), 12, 24);
        } else {
            return LocalDate.now()
                    .plusMonths(boardRegDTO.getOpenDate().getPlusMonths());
        }
    }

    public List<BoardResponse> getBoardList(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.EMAIL_NOT_FOUND));

        List<Board> boardList = boardRepository.findByMemberId(member.getId());

        return boardList.stream()
                .map(board -> BoardResponse.builder()
                        .id(board.getId())
                        .name(board.getBoardName())
                        .openDate(board.getOpenDate())
                        .build()).collect(Collectors.toList());
    }

    public BoardResponse getBoard(Long id, String email) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BizException(ErrorCode.BOARD_NOT_FOUND));

        if (!board.getMember().getEmail().equals(email)) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_BOARD);
        }

        return BoardResponse.builder()
                .id(board.getId())
                .name(board.getBoardName())
                .openDate(board.getOpenDate())
                .build();
    }

    public void updateBoard(Long id, String email, BoardUpdateDTO boardUpdateDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BizException(ErrorCode.BOARD_NOT_FOUND));

        if (!board.getMember().getEmail().equals(email)) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_BOARD, "저금통 생성자만 변경이 가능합니다.");
        }

        boardRepository.save(Board.builder()
                .id(board.getId())
                .boardName(boardUpdateDTO.getName())
                .openDate(board.getOpenDate())
                .registerAt(board.getRegisterAt())
                .updateAt(LocalDateTime.now())
                .member(board.getMember())
                .build());
    }

    public void deleteBoard(Long id, String email) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BizException(ErrorCode.BOARD_NOT_FOUND));

        if (!board.getMember().getEmail().equals(email)) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_BOARD, "저금통 생성자만 삭제가 가능합니다.");
        }

        boardRepository.delete(board);
    }
}
