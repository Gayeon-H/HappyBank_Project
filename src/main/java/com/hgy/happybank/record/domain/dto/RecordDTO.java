package com.hgy.happybank.record.domain.dto;

import com.hgy.happybank.board.domain.Board;
import com.hgy.happybank.record.domain.Record;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {

    private String title;
    private String contents;
    private String imgURL;
    private LocalDate regDate;
    private Board board;

    public Record toRecord() {
        return Record.builder()
                .title(this.title)
                .contents(this.contents)
                .imgURL(this.imgURL)
                .regDate(this.regDate)
                .board(this.board)
                .build();
    }
}
