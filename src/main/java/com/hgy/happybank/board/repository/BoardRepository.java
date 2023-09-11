package com.hgy.happybank.board.repository;

import com.hgy.happybank.board.domain.Board;
import com.hgy.happybank.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    int countByMember(Member member);

    List<Board> findByMemberId(Long memberId);

}
