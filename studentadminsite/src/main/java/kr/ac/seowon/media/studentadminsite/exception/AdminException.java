package kr.ac.seowon.media.studentadminsite.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminException extends IllegalStateException{

    public AdminException(String s) {
        super(s);
        log.error("contact AdminException.class = {}",s );
    }
}
