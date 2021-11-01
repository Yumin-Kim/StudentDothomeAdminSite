package kr.ac.seowon.media.studentadminsite.exception.utilexception;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class UtilJdbcConnectionException extends SQLException {
    public UtilJdbcConnectionException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
        log.error(reason);
    }

    public UtilJdbcConnectionException(ErrorCode ErrorCode, String message, int errorCode) {
        super(ErrorCode.getMessage(), message, errorCode);
    }
}
