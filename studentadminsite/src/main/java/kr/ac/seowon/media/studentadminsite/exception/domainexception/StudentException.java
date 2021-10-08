package kr.ac.seowon.media.studentadminsite.exception.domainexception;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentException extends IllegalStateException{
    public StudentException(String s) {
        super(s);
    }

    public StudentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

}
