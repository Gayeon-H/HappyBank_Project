package com.hgy.happybank.board.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public enum OpenDate {
    ONE_MONTH(1),
    THREE_MONTH(3),
    SIX_MONTH(6),
    TWELVE_MONTH(12),
    YEAR_END(0);

    private final int plusMonths;

    public static LocalDate getOpenDate(OpenDate openDate) {
        if (openDate == OpenDate.YEAR_END) {
            return LocalDate.of(LocalDate.now().getYear(), 12, 24);
        } else {
            return LocalDate.now().plusMonths(openDate.getPlusMonths());
        }
    }

}
