package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> , StudentCustomRepository {
}
