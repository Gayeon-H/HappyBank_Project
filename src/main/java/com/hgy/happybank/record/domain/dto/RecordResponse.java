package com.hgy.happybank.record.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordResponse {

    private long id;
    private String title;
    private String contents;
    private String imgURL;
    private LocalDate regDate;

}
