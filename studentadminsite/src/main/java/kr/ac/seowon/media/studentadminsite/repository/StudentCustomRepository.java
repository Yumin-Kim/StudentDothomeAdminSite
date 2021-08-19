package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.Student;

import java.util.Optional;

public interface StudentCustomRepository {
    Optional<Student> savec(Student student);

}
