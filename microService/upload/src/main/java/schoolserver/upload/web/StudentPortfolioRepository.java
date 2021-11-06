package schoolserver.upload.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentPortfolioRepository extends JpaRepository<StudentPortfolio,Integer> {

    Optional<StudentPortfolio> findByStudentCode(Integer studentCode);

    Optional<StudentPortfolio> findByNameAndStudentCode(String name, Integer studentCode);

    StudentPortfolio findByName(String name);
}
