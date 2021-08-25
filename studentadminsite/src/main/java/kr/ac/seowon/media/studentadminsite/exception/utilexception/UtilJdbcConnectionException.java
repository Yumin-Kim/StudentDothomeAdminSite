package kr.ac.seowon.media.studentadminsite.exception.utilexception;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class UtilJdbcConnectionException extends SQLException {
    public UtilJdbcConnectionException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
        log.error(reason);
    }
}
