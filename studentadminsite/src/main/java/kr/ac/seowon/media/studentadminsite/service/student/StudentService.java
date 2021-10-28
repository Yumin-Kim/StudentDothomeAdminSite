package kr.ac.seowon.media.studentadminsite.service.student;

import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentSiteInfoException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final SiteInfoRespository siteInfoRespository;
    private final UtilConfigure utilConfigure;

    public StudentDtoRes.BasicStudent findStudentCodeAndName(StudentReq.StudentDto studentDto) {
        Student student = getStudent(studentDto);
        return new StudentDtoRes.BasicStudent(student);
    }


    public StudentDtoRes.DefaultStudent findStudentCode(String name, Integer studentCode) {
        Student student = studentRepository.findByStudentCodeAndName(studentCode, name)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        return new StudentDtoRes.DefaultStudent(student);

    }

    private Student getStudent(StudentReq.StudentDto studentDto) {
        return studentRepository.findByStudentCodeAndName(studentDto.getStudentCode(), studentDto.getName())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
    }


}


