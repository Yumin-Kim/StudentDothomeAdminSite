package kr.ac.seowon.media.studentadminsite.exception;

import java.util.List;

public class InsertDuplicateException extends IllegalStateException {
    public InsertDuplicateException(String duplicateStudents) {
        super(duplicateStudents);
    }
}
