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

    STUDENT_NOT_FOUND("입력한 학생 정보를 다시 확인 해주세요",1),
    STUDENTCODE_DUPLICATE_ERROR("중복되는 학번을 입력하였습니다.",12),
    STUDENT_HAS_DATA("존재하는 학생입니다.", 13),
    STUDENT_ID_NOT_FOUND("비밀 번호 및 학번을 확인해주세요",14),
    SSH_CONNECTION_ERROR("SSH 연결 실패하였습니다", 3),
    SSH_DUPLICATE_DOMAIN("중복되는 도메인 정보를 입력 하였습니다.", 31),
    SSH_CONTACT_DOMAIN("도메인 접근 오류가 발생하였습니다.", 32),
    JDBC_CONNECTION_ERROR("jdbc 연결 에러 발생", 40),
    JDBC_COMMIT_ROLLBACK("jdbc commit 에러 발생하여 rollback이 발생",41),
    IMAGE_UPLOAD_ERROR("이미지 업로드 실패", 5);
    private String message;
    private Integer statusCode;

    ErrorCode(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
