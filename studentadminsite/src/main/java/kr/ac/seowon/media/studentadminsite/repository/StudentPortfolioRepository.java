package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.StudentPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentPortfolioRepository extends JpaRepository<StudentPortfolio , Integer> {
    Optional<StudentPortfolio> findByStudentCode(Integer studentCode);
}
