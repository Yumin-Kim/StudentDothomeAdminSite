package kr.ac.seowon.media.studentadminsite.repository;


import kr.ac.seowon.media.studentadminsite.domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @PersistenceContext
    EntityManager em;


    @Override
    public Optional<Student> savec(Student student) {
        em.persist(student);
//        em.flush();
//        em.clear();
        Student student1 = em.find(Student.class, student.getId());
        return Optional.ofNullable(student1);
    }
}
