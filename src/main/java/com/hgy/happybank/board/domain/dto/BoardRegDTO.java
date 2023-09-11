package com.hgy.happybank.board.domain.dto;

import com.hgy.happybank.board.type.OpenDate;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
public class BoardRegDTO {

    @NotNull
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private OpenDate openDate;
}
