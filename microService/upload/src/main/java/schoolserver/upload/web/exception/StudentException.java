package schoolserver.upload.web.exception;

public class StudentException extends IllegalStateException {
    public StudentException(String s) {
        super(s);
    }

    public StudentException(ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
