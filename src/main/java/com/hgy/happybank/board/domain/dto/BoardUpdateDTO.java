package com.hgy.happybank.board.domain.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUpdateDTO {

    @NotNull
    private String name;

}
