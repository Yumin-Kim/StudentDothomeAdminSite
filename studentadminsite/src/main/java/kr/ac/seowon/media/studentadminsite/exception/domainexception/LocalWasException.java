package kr.ac.seowon.media.studentadminsite.exception.domainexception;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;

public class LocalWasException extends IllegalStateException{

    public LocalWasException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
