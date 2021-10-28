package kr.ac.seowon.media.studentadminsite.exception.domainexception;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;

public class StudentSiteInfoException extends IllegalStateException {
    public StudentSiteInfoException(String s) {
        super(s);
    }

    public StudentSiteInfoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
