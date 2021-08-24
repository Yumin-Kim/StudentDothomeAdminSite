package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AdminRepository adminRepository;

    @BeforeEach
    void configure(){
        AdminObserveReq.BasicStudentDto basicStudentDto1 = new AdminObserveReq.BasicStudentDto("name",201610302);
        AdminObserveReq.BasicStudentDto basicStudentDto2 = new AdminObserveReq.BasicStudentDto("name",201610301);
        Admin admin = Admin.createAdmin("name", "1234", "asd", "asd");
        Student student1 = Student.createStudent(basicStudentDto1, admin);
        Student student2 = Student.createStudent(basicStudentDto2, admin);
        adminRepository.save(admin);
        studentRepository.save(student1);
        studentRepository.save(student2);

    }

    @Test
    @DisplayName("StudentRepository QueryMethod")
    void start_1() throws Exception{
        //given
        //when
        Student student = studentRepository.findByStudentCodeAndName(201610302, "name").get();
        List<Student> findStudents = studentRepository.findByStudentCodeIn(List.of(201610302, 201610301));
        //then
        assertEquals(student.getStudentCode(),201610302);
        assertEquals(student.getInSchool(), true);
        assertEquals(student.getIsDeleted(), false);
        assertEquals(findStudents.size(),2);
    }

}