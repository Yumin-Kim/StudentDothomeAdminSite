package kr.ac.seowon.media.studentadminsite.service;

import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.StudnetException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    public StudentDao.BasicStudent createStudent(StudentReq.StudentDto studentDto) {
        Admin admin = adminRepository.findByHasCode(studentDto.getHashCode())
                .orElseThrow(() -> new StudnetException("승인하지 않은 키입니다."));
        Student student = studentRepository.findByStudentCodeAndName(studentDto.getStudentCode(), studentDto.getName())
                .orElseThrow(() -> new StudnetException("존재하지 않는 학생입니다."));
        SiteInfo craetSiteInfo = SiteInfo.createSiteInfo(studentDto.getName(), "s" + studentDto.getStudentCode());
        student.modifyStudent(studentDto,admin,craetSiteInfo);
        return new StudentDao.BasicStudent(student);
    }

}
