package kr.ac.seowon.media.studentadminsite.service.student;

import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.admin.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.student.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.student.StudentRepository;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentQueryService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final SiteInfoRespository siteInfoRespository;
    private final UtilConfigure utilConfigure;

    public StudentDtoRes.BasicStudent findStudentCodeAndName(StudentReq.StudentDto studentDto) {
        Student student = getStudent(studentDto.getName(),studentDto.getStudentCode());
        return new StudentDtoRes.BasicStudent(student);
    }


    public StudentDtoRes.DefaultStudent findStudentCode(String name, Integer studentCode) {
        Student student = studentRepository.findByStudentCodeAndName(studentCode, name)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        return new StudentDtoRes.DefaultStudent(student);

    }

    public StudentDtoRes.StudentFullDto findStudentInfo(String name, Integer studentCode) {
        final Student student = getStudent(name, studentCode);
        if(student.getSiteInfo() == null) throw new StudentException(ErrorCode.STUDENT_INFO_NOT_REGISTER);
        return new StudentDtoRes.StudentFullDto(student);
    }

    private Student getStudent(String name,Integer studentCode) {
        return studentRepository.findByStudentCodeAndName(studentCode,name)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
    }


}


