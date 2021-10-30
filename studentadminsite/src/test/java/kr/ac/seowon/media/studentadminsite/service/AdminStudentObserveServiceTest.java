package kr.ac.seowon.media.studentadminsite.service;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.AdminObserveException;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.InsertDuplicateException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.admin.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.student.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.student.StudentRepository;
import kr.ac.seowon.media.studentadminsite.service.adminobserve.AdminObserveCommandService;
import kr.ac.seowon.media.studentadminsite.service.adminobserve.AdminStudentObserveService;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminStudentObserveServiceTest {

    @InjectMocks
    AdminStudentObserveService adminStudentObserveService;

    @InjectMocks
    AdminObserveCommandService adminObserveCommandService;

    @Mock
    StudentRepository studentRepository;

    @Mock
    AdminRepository adminRepository;

    @Mock
    SiteInfoRespository siteInfoRespository;

    @InjectMocks
    UtilConfigure utilConfigure;
//
//    @InjectMocks
//    JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
    @Test
    @DisplayName("학생 추가 _예외 및 통과 로직 포함")
    void start_1() throws Exception{
        //given
        Admin admin = Admin.createAdmin("name", "qwe", "qwe","asdasd");
        given(adminRepository.findById(any()))
                .willReturn(Optional.of(admin))
                .willReturn(Optional.empty())
                .willReturn(Optional.of(admin))
                .willReturn(Optional.of(admin));
        AdminObserveReq.BasicStudentDto basicStudentDto = new AdminObserveReq.BasicStudentDto("name", 201610309);
        Student student = Student.createStudent(basicStudentDto, admin);
        given(studentRepository.findByStudentCode(any()))
                .willReturn(Optional.empty())
                .willReturn(Optional.of(student));
        //when
        adminObserveCommandService.insertStudentInfo(1, basicStudentDto);
        //then
        assertThrows(AdminObserveException.class, () -> adminObserveCommandService.insertStudentInfo(2, basicStudentDto));
    }

    @Test
    @DisplayName("학생들 추가 _예외 및 통과 로직 포함")
    void start_2() throws Exception{
        //given
        Admin admin = Admin.createAdmin("name", "qwe", "qwe","asdasd");
        given(adminRepository.findById(any()))
                .willReturn(Optional.of(admin))
                .willReturn(Optional.empty())
                .willReturn(Optional.of(admin))
                .willReturn(Optional.of(admin));
        AdminObserveReq.BasicStudentDto basicStudentDto1 = new AdminObserveReq.BasicStudentDto("name", 201610309);
        AdminObserveReq.BasicStudentDto basicStudentDto2 = new AdminObserveReq.BasicStudentDto("name", 201610301);
        AdminObserveReq.BasicStudentDto basicStudentDto3 = new AdminObserveReq.BasicStudentDto("name", 201610309);
        AdminObserveReq.BasicStudentDto basicStudentDto4 = new AdminObserveReq.BasicStudentDto("name", 201610309);
        Student student1 = Student.createStudent(basicStudentDto1, admin);
        Student student2 = Student.createStudent(basicStudentDto2, admin);
        given(studentRepository.findByStudentCodeIn(any()))
                .willReturn(Lists.emptyList())
                .willReturn(List.of(student1, student2))
                .willReturn(Lists.emptyList());

        //when
        adminObserveCommandService.concurrentInsertStudentsInfo(1, List.of(basicStudentDto1,basicStudentDto2));
        //then
        assertThrows(AdminObserveException.class, () -> adminObserveCommandService.concurrentInsertStudentsInfo(2, List.of(basicStudentDto1,basicStudentDto2)));
        assertThrows(InsertDuplicateException.class, () -> adminObserveCommandService.concurrentInsertStudentsInfo(2, List.of(basicStudentDto1,basicStudentDto2)));
        assertThrows(AdminObserveException.class, () -> adminObserveCommandService.concurrentInsertStudentsInfo(2, List.of(basicStudentDto3,basicStudentDto4)));
    }

    @Test
    @DisplayName("학생 정보 삭제_ 통과 로직")
    void deleteStudentsInfo() throws Exception{
        // given

        AdminObserveReq.BasicStudentDto n1 = new AdminObserveReq.BasicStudentDto("n1", 2016);
        AdminObserveReq.BasicStudentDto n2 = new AdminObserveReq.BasicStudentDto("n2", 2016);
        AdminObserveReq.BasicStudentDto n3_siteInfo = new AdminObserveReq.BasicStudentDto("n2", 2016);
        Admin admin = Admin.createAdmin("name", "qwe", "qwe","asdasd");
        SiteInfo siteInfo = SiteInfo.createSiteInfo("name", "name2");
        Student student1 = Student.createStudent(n1, admin);
        Student student2 = Student.createStudent(n2, admin);
        Student student3 = Student.createStudent(n3_siteInfo, admin);
        StudentReq.StudentDto studentDto = new StudentReq.StudentDto();
        student3.modifyStudent(studentDto,admin,siteInfo);
        given(studentRepository.findByIdIn(any()))
                .willReturn(List.of(student1, student2))
                .willReturn(List.of(student3));
        try (
                MockedConstruction<JdbcRootPermition> jdbcRootPermitionMockedConstruction = mockConstruction(JdbcRootPermition.class);
                MockedConstruction<SSHConnection> sshConnectionMockedConstruction = mockConstruction(SSHConnection.class)
        ) {
            final JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
            doNothing().when(jdbcRootPermition).deleteDatabase(any());
            doNothing().when(siteInfoRespository).delete(any());
            assertThrows(StudentException.class, () -> adminObserveCommandService.deleteStudentsInfo(List.of(1, 2)));
            adminObserveCommandService.deleteStudentsInfo(List.of(3));
        }

        // when

        // then
    }



}