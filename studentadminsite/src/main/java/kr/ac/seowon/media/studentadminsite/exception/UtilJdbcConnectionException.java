package kr.ac.seowon.media.studentadminsite.exception;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class UtilJdbcConnectionException extends SQLException {

    private final String MESSAGE = "Root jdbc connection error";

    public UtilJdbcConnectionException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
        log.error(MESSAGE);
    }
}
