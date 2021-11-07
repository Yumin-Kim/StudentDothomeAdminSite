package schoolserver.upload.web.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    STUDENT_NOT_FOUND("등록되어 있는 학생이 아닙니다"),
    UPLOAD_ERROR("업로드 에러가 발생했습니다.");

    private String description;

    ErrorCode(String description) {
        this.description = description;
    }
}
