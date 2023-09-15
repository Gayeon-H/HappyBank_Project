package com.hgy.happybank.record.repository;

import com.hgy.happybank.record.domain.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Page<Record> findByBoardId(long boardId, Pageable pageable);

    Optional<Record> findByIdAndBoardId(long id, long boardId);

}
