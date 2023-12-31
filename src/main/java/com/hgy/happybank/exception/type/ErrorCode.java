package com.hgy.happybank.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원정보가 존재하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    OVER_REGISTERED_BOARD(HttpStatus.CONFLICT, "등록 가능한 저금통 수를 초과하였습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 저금통입니다."),
    NO_RIGHT_ABOUT_THIS_BOARD(HttpStatus.CONFLICT, "접근이 허용되지 않은 회원입니다."),
    NOT_POSSIBLE_TO_OPEN_BEFORE_OPENING_DATE(HttpStatus.CONFLICT, "개봉일 이전에는 조회할 수 없습니다."),
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 기록입니다."),
    ALREADY_REGISTER_RECORD_TODAY(HttpStatus.CONFLICT, "오늘 기록은 이미 등록되었습니다."),
    FRIEND_REQUEST_ERROR(HttpStatus.CONFLICT, "친구요청 관련 오류입니다."),
    NOT_FRIEND(HttpStatus.CONFLICT, "친구만 저금통 공유가 가능합니다."),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 질문입니다."),
    NO_RIGHT_ABOUT_THIS_QUESTION(HttpStatus.CONFLICT, "등록자만 수정이 가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
