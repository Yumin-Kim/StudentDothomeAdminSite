package kr.ac.seowon.media.studentadminsite.service;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.AdminObserveException;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.InsertDuplicateException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdminStudentObserveServiceTest {

    @InjectMocks
    AdminStudentObserveService adminStudentObserveService;

    @Mock
    StudentRepository studentRepository;

    @Mock
    AdminRepository adminRepository;

    @BeforeEach
    void configure(){
        Admin admin = Admin.createAdmin("name", "qwe", "qwe","asdasd");
        given(adminRepository.findById(any()))
                .willReturn(Optional.of(admin))
                .willReturn(Optional.empty())
                .willReturn(Optional.of(admin))
                .willReturn(Optional.of(admin));


    }

    @Test
    @DisplayName("학생 추가 _예외 및 통과 로직 포함")
    void start_1() throws Exception{
        //given
        AdminObserveReq.BasicStudentDto basicStudentDto = new AdminObserveReq.BasicStudentDto("name", 201610309);
        Admin admin = Admin.createAdmin("name", "1234", "asd", "asd");
        Student student = Student.createStudent(basicStudentDto, admin);
        given(studentRepository.findByStudentCodeAndName(any(), any()))
                .willReturn(Optional.empty())
                .willReturn(Optional.of(student));
        //when
        adminStudentObserveService.insertStudentInfo(1, basicStudentDto);
        //then
        assertThrows(AdminObserveException.class, () -> adminStudentObserveService.insertStudentInfo(2, basicStudentDto));
    }

    @Test
    @DisplayName("학생들 추가 _예외 및 통과 로직 포함")
    void start_2() throws Exception{
        //given
        AdminObserveReq.BasicStudentDto basicStudentDto1 = new AdminObserveReq.BasicStudentDto("name", 201610309);
        AdminObserveReq.BasicStudentDto basicStudentDto2 = new AdminObserveReq.BasicStudentDto("name", 201610301);
        AdminObserveReq.BasicStudentDto basicStudentDto3 = new AdminObserveReq.BasicStudentDto("name", 201610309);
        AdminObserveReq.BasicStudentDto basicStudentDto4 = new AdminObserveReq.BasicStudentDto("name", 201610309);
        Admin admin = Admin.createAdmin("name", "1234", "asd", "asd");
        Student student1 = Student.createStudent(basicStudentDto1, admin);
        Student student2 = Student.createStudent(basicStudentDto2, admin);
        given(studentRepository.findByStudentCodeIn(any()))
                .willReturn(Lists.emptyList())
                .willReturn(List.of(student1, student2))
                .willReturn(Lists.emptyList());

        //when
        adminStudentObserveService.concurrentInsertStudentsInfo(1, List.of(basicStudentDto1,basicStudentDto2));
        //then
        assertThrows(AdminObserveException.class, () -> adminStudentObserveService.concurrentInsertStudentsInfo(2, List.of(basicStudentDto1,basicStudentDto2)));
        assertThrows(InsertDuplicateException.class, () -> adminStudentObserveService.concurrentInsertStudentsInfo(2, List.of(basicStudentDto1,basicStudentDto2)));
        assertThrows(AdminObserveException.class, () -> adminStudentObserveService.concurrentInsertStudentsInfo(2, List.of(basicStudentDto3,basicStudentDto4)));
    }



}