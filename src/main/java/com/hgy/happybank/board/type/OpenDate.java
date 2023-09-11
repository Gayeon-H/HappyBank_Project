package com.hgy.happybank.board.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpenDate {
    ONE_MONTH(1),
    THREE_MONTH(3),
    SIX_MONTH(6),
    TWELVE_MONTH(12),
    YEAR_END(0);

    private final int plusMonths;
}
