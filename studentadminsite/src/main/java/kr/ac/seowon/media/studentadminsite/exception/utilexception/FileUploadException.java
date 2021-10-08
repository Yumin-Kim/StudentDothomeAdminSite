package kr.ac.seowon.media.studentadminsite.exception.utilexception;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;

import java.io.IOException;

public class FileUploadException extends Exception {

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

}
