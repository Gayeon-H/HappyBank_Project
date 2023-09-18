package com.hgy.happybank.friend.repository;

import com.hgy.happybank.friend.domain.BoardShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardShareRepository extends JpaRepository<BoardShare, Long> {

    List<BoardShare> findByBoardId(long boardId);
}
