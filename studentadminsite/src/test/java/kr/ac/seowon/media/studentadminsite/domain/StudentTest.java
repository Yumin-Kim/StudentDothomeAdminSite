package kr.ac.seowon.media.studentadminsite.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentReq;
import kr.ac.seowon.media.studentadminsite.repository.student.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static kr.ac.seowon.media.studentadminsite.domain.QStudent.student;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@DataJpaTest
class StudentTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SiteInfoRespository siteInfoRespository;

    @BeforeEach
    void configure(){
        AdminObserveReq.BasicStudentDto basicStudentDto = new AdminObserveReq.BasicStudentDto("yumin", 201532323);
        Admin admin = Admin.createAdmin("name", "1234", "asd", "asd");
        SiteInfo siteInfo = SiteInfo.createSiteInfo("domain", "databaseName");
        for (int i = 0; i < 50; i++) {
            AdminObserveReq.BasicStudentDto basicStudentDto1 = new AdminObserveReq.BasicStudentDto("yumin" +i, 201632323+i);
            Student student2 = Student.createStudent(basicStudentDto1, admin);
            em.persist(student2);
        }
        Student student1 = Student.createStudent(basicStudentDto, admin);

        StudentReq.StudentDto studentDto = new StudentReq.StudentDto("yumin123", 201732323, "qweqwe", "qweqwe", "qwe", "qwe");
        student1.modifyStudent(studentDto,admin,siteInfo);
        em.persist(admin);
        em.persist(siteInfo);
        em.persist(student1);
    }

    @Test
    @DisplayName("Connection Test code")
    void start_1() throws Exception{
        //given
        Student singleResult = em.createQuery("select s from Student s join fetch s.siteInfo si" +
                " where s.name = :name and s.studentCode = :code", Student.class)
                .setParameter("name", "yumin")
                .setParameter("code", 201532323)
                .getSingleResult();
        siteInfoRespository.delete(singleResult.getSiteInfo());
        singleResult.disabledStudent(true);
        //when
        //then
    }

    @Test
    @DisplayName("querydsl 테스트 코드")
    void start_querydsl() throws Exception{
        //given
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        Student querydslFindStudent = jpaQueryFactory.selectFrom(student)
                .where(student.name.eq("yumin"))
                .fetchOne();
        //when
        assertEquals(querydslFindStudent.getName(),"yumin");
        //then
    }





}