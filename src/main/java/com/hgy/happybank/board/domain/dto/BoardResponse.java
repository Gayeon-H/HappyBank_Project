package com.hgy.happybank.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class BoardResponse {

    private Long id;
    private String name;
    private LocalDate openDate;
}
