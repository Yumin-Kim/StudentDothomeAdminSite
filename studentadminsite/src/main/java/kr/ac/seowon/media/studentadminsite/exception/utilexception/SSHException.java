package kr.ac.seowon.media.studentadminsite.exception.utilexception;


import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;

public class SSHException extends RuntimeException {
    public SSHException(String message) {
        super(message);
    }

    public SSHException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
