package kr.ac.seowon.media.studentadminsite.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudnetException extends IllegalStateException{
    public StudnetException(String s) {
        super(s);
        log.info("StudnetException.class = {}",s);
    }
}
