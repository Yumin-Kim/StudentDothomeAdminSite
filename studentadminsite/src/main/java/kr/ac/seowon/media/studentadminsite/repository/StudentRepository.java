package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> , StudentCustomRepository {

    @EntityGraph(attributePaths = {"siteInfo"})
    Optional<Student> findByStudentCodeAndName(Integer studentCode, String name);

    List<Student> findByStudentCodeIn(List<Integer> studentCodes);

    @EntityGraph(attributePaths = {"siteInfo"})
    Optional<Student> findSiteInfoById(Integer studentId);

    @EntityGraph(attributePaths = {"siteInfo"})
    List<Student> findByIdIn(List<Integer> studentIds);

    //left join fetch >> left outer join
    @Override
    @EntityGraph(attributePaths = {"siteInfo", "admin"})
    Page<Student> findAll(Pageable pageable);
}
