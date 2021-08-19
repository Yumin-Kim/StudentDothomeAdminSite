package kr.ac.seowon.media.studentadminsite.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Rollback(value = false)
@Transactional
@SpringBootTest
class StudentTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("Connection Test code")
    void start_1() throws Exception{
        //given
        Student student = Student.createStudent("name", 2016012312);
        //when
        em.persist(student);
//        em.flush();
//        em.clear();
        Student student1 = em.find(Student.class, student.getId());
        //then
        assertEquals(student1.getName(), student.getName());
    }


}