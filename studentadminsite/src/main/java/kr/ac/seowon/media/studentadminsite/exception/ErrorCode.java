package kr.ac.seowon.media.studentadminsite.exception;

import lombok.Getter;

//EXCEPTION_message 정의

//Exception 필요

// Exception 정의 해야함

@Getter
public enum ErrorCode {

    STUDENT_NOT_FOUND("학생 정보가 존재하지 않습니다.",1);

    private String message;
    private Integer statusCode;

    ErrorCode(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
