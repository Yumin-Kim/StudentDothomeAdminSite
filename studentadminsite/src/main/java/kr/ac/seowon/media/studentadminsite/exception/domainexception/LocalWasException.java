package kr.ac.seowon.media.studentadminsite.exception.domainexception;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;

public class LocalWasException extends IllegalStateException{
    private String message;

    public LocalWasException(ErrorCode errorCode ) {
        super(errorCode.getMessage());
    }

    public LocalWasException(ErrorCode errorCode , String errorLog ) {
        super(errorCode.getMessage());
        this.message = errorLog;
    }
}
