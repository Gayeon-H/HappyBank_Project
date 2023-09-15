package com.hgy.happybank.record.domain;

import com.hgy.happybank.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contents;
    private LocalDate regDate;

    @Column(name = "img_url")
    private String imgURL;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;

}
