package schoolserver.upload.web.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    STUDENT_NOT_FOUND("등록되어 있는 학생이 아닙니다");

    private String description;

    ErrorCode(String description) {
        this.description = description;
    }
}
