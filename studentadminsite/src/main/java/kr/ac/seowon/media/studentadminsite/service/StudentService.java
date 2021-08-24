package kr.ac.seowon.media.studentadminsite.service;

import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final SiteInfoRespository siteInfoRespository;
    private final UtilConfigure utilConfigure;

    //Todo 데이터 베이스 계정 , ssh 계정 생성 코드 필요
    public StudentDao.BasicStudent createStudent(StudentReq.StudentDto studentDto) {
        Admin admin = adminRepository.findByHashCode(studentDto.getHashCode())
                .orElseThrow(() -> new StudentException("승인하지 않은 키입니다."));
        Student student = getStudent(studentDto);
        if (student.getSiteInfo() != null) throw new StudentException("존재하는 계정입니다");
        SiteInfo createSiteInfo = SiteInfo.createSiteInfo(studentDto.getName(), "s" + studentDto.getStudentCode());
        siteInfoRespository.save(createSiteInfo);
        student.modifyStudent(studentDto,admin,createSiteInfo);
        return new StudentDao.BasicStudent(student);
    }

    public StudentDao.BasicStudent findStudentCodeAndName(StudentReq.StudentDto studentDto) {
        Student student = getStudent(studentDto);
        return new StudentDao.BasicStudent(student);
    }


    public StudentDao.BasicStudent findStudentCode(String name, Integer studentCode) {
        Student student = studentRepository.findByStudentCodeAndName(studentCode, name)
                .orElseThrow(() -> new StudentException("존재하지 않는 학생입니다."));
        return new StudentDao.BasicStudent(student);

    }

    private Student getStudent(StudentReq.StudentDto studentDto) {
        return studentRepository.findByStudentCodeAndName(studentDto.getStudentCode(), studentDto.getName())
                .orElseThrow(() -> new StudentException("존재하지 않는 학생입니다."));
    }

    //TODO
    // 1. SSH로 수정
    // 2. siteInfo 수정
    // 3.
    public StudentDao.BasicStudent modifyStudentInfo(Integer studentId, StudentReq.StudentDto studentDto, StudentReq.SiteInfoDto siteInfoDto) {
        Student student = studentRepository.findSiteInfoById(studentId)
                .orElseThrow(() -> new StudentException("존재하지 않는 학생입니다."));
        if (student.getSiteInfo() == null) throw new StudentException("아직 등록하지 않은 계정입니다.");
        if (!student.getSiteInfo().getDomainName().equals(siteInfoDto.getOriginDomain())) throw new StudentException("기존 도메인을 잘못 입력하셨습니다.");
        //ssh 접근하여 유저명 , 도메인 정보 수정
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        sshConnection.modifyStudentInfo(siteInfoDto);
        //siteInfo database 정보 수정
        student.getSiteInfo().modifySiteInfo(siteInfoDto);
        //student database 정보 수정
        student.modifyStudent(studentDto,null,null);
        return new StudentDao.BasicStudent(student);
    }
}


