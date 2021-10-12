package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.StudentPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentPortfolioRepository extends JpaRepository<StudentPortfolio , Integer> {

    Optional<StudentPortfolio> findByStudentCode(Integer studentCode);

    Optional<StudentPortfolio> findByStudentCodeAndName(Integer studentCode, String name);

    Optional<StudentPortfolio> findByStudentCodeAndNameAndPassword(Integer studentCode, String name, String password);

    Optional<StudentPortfolio> findByStudentCodeAndPassword(Integer studentCode, String password);
}
