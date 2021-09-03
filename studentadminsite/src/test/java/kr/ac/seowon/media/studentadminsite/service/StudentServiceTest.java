package kr.ac.seowon.media.studentadminsite.service;

import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.utilexception.SSHException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    AdminRepository adminRepository;

    @Mock
    SiteInfoRespository siteInfoRespository;

    @Mock
    UtilConfigure utilConfigure;

    @InjectMocks
    StudentService studentService;

    private static MockedStatic<Pattern> patternMockedStatic;
    @BeforeEach
    void setting(){
        patternMockedStatic = mockStatic(Pattern.class);
    }
    @Test
    @DisplayName("학생 생성 비즈로직_ 정규 표현식")
    void start_1_crateStudent() throws Exception{
        //given
        //자바스크립트는 포함 여부를 확인하지만 java는 조건에 일치하는지를 판단??
        String test = "asdasdasdASDASDasd";
        String regex1 = "^[a-z]*$";
        String regex2 = "^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|A-Z]*$";
        boolean matches = Pattern.matches(regex1, test);
        System.out.println("matches = " + matches);
        System.out.println(" = " + test.matches(regex1));
        System.out.println(" = " + test.matches(regex2));
        String pattern = "[a-z]";
        System.out.println("ab".matches(pattern));
        System.out.println("abc".matches(pattern));
        //when

        //then
    }

    @Test
    @DisplayName("학생 생성 비즈니스 로직 >> ssh 연결 에서 Exception 발생")
    void start_1_craeteStudent() throws Exception{
        //given
        Admin admin = Admin.createAdmin("name", "qwe", "qwe","asdasd");
        SiteInfo siteInfo = SiteInfo.createSiteInfo("DASDASD", "database");
        StudentReq.StudentDto studentDto = new StudentReq.StudentDto("name", 201610309, "asd", "asd", "asd", "aasd");
        StudentReq.SiteInfoDto siteInfoDto = new StudentReq.SiteInfoDto("database", "asdas", null);
        AdminObserveReq.BasicStudentDto basicStudentDto = new AdminObserveReq.BasicStudentDto("name", 201610309);
        Student student1 = Student.createStudent(basicStudentDto, admin);
        given(adminRepository.findByHashCode(any()))
                .willReturn(Optional.of(admin));
        given(studentRepository.findByStudentCodeAndName(any(), any()))
                .willReturn(Optional.of(student1));
        given(Pattern.matches("^[a-z]$", siteInfoDto.getDomainName()))
                .willReturn(true);
        given(siteInfoRespository.findByDomainName(any()))
                .willReturn(Optional.empty());
        //when
        //then
        assertThrows(SSHException.class,()->studentService.createStudent(studentDto, siteInfoDto));

    }
    @AfterEach
    void endPoint(){
        patternMockedStatic.close();
    }


}
