package com.hgy.happybank.exception;

import com.hgy.happybank.exception.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BizException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public BizException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
