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
    STUDENT_NOT_PERMISSION("승인하지 않은 키입니다.",15),
    STUDENT_ID_DISABLED("비활성화 계정입니다.",15),
    STUDENT_NOT_CORRECT_DOMAIN("domainName은 대문자 , 특수 문자 , 뛰어쓰기가 존재하면 안됩니다.",16),
    STUDENT_INFO_NOT_REGISTER("아직 사이트 정보를 등록하지 않은 계정입니다.", 17),
    STUDENT_NOT_CORRECT_SITEINFO_DOMAIN("기존 도메인을 잘못 입력하셨습니다.", 19),
    STUDENT_NOT_SITEINFO("현 조회 학생은 사이트 정보가 존재 하지 않습니다.", 111),
    LOCAL_DUPLICATE_PORT("중복 되는 포트 입니다.", 121),
    LOCAL_NOT_SELECT_DEPLOY_METHOD("Deploy 방식을 선택하지 않았습니다.", 122),
    LOCAL_DEPLOY_TEST_ERROR("배포를 위한 테스트에 오류가 발생하였습니다.", 123),
    LOCAL_DEPLOY_NOTWRITE_ERROR_LOG("배포간 에러 로그가 작성되지 않았습니다.", 124),
    ADMIN_NOT_FOUND("존재하지 않는 관리자 입니다.", 2),
    ADMIN_NOT_HAS_SITEINFO("선택한 학생은 사이트 정보가 존재하지 않습니다.", 21),
    ADMIN_NOT_FOUND_STUDENT("존재하지 않는 학생을 조회하였습니다.", 22),
    ADMIN_DUPLICATE_NAME("존재하는 이름 입니다.", 22),
    ADMIN_DUPLICATE_HASHCODE("존재하는 hashcode 입니다.", 22),
    SSH_CONNECTION_ERROR("SSH 연결 실패하였습니다", 3),
    SSH_DUPLICATE_DOMAIN("중복되는 도메인 정보를 입력 하였습니다.", 31),
    SSH_CONTACT_DOMAIN("도메인 접근 오류가 발생하였습니다.", 32),
    JDBC_CONNECTION_ERROR("jdbc 연결 에러 발생", 40),
    JDBC_COMMIT_ROLLBACK("jdbc commit 에러 발생하여 rollback이 발생",41),
    JDBC_USER_DATABASE_INSERT_ERROR("UserDatabase 접근간 insert 에러 발생", 42),
    IMAGE_UPLOAD_ERROR("이미지 업로드 실패", 5);
    private String message;
    private Integer statusCode;

    ErrorCode(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
