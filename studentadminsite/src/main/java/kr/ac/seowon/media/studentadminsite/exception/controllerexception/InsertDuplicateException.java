package kr.ac.seowon.media.studentadminsite.exception.controllerexception;

import java.util.List;

public class InsertDuplicateException extends IllegalStateException {
    public InsertDuplicateException(String duplicateStudents) {
        super(duplicateStudents);
    }
}
