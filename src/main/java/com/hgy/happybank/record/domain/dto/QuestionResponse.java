package com.hgy.happybank.record.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponse {

    private long id;
    private String contents;
    private String memberNickname;
    private LocalDateTime registerAt;
    private LocalDateTime updateAt;
}
