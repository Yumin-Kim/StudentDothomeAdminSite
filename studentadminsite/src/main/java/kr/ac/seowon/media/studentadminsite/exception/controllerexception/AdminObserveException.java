package kr.ac.seowon.media.studentadminsite.exception.controllerexception;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;

public class AdminObserveException extends IllegalStateException {
    public AdminObserveException(String message) {
        super(message);
    }

    public AdminObserveException(ErrorCode errorCode){
        super(errorCode.getMessage());

    }
}
