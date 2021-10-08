package kr.ac.seowon.media.studentadminsite.exception;

import lombok.Getter;

//EXCEPTION_message 정의

//Exception 필요

// Exception 정의 해야함
// 1_ 학생
// 2_ 관리자
// 3_ ssh
// 4_ JDBC 에러
// 5_ 유틸성
@Getter
public enum ErrorCode {

    STUDENT_NOT_FOUND("학생 정보가 존재하지 않습니다.",1),
    STUDENTCODE_DUPLICATE_ERROR("중복되는 학번을 입력하였습니다.",12),
    IS_STUDENT_DATA("존재하는 학생입니다.",13),
    IMAGE_UPLOAD_ERROR("이미지 업로드 실패", 5);
    private String message;
    private Integer statusCode;

    ErrorCode(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
