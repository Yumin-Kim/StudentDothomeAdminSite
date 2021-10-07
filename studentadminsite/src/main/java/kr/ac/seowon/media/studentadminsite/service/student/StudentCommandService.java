package kr.ac.seowon.media.studentadminsite.service.student;

import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
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
@Transactional
@RequiredArgsConstructor
public class StudentCommandService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final SiteInfoRespository siteInfoRespository;
    private final UtilConfigure utilConfigure;

    public StudentDao.BasicStudent createStudent(StudentReq.StudentDto studentDto, StudentReq.SiteInfoDto siteInfoDto) {
        Admin admin = adminRepository.findByHashCode(studentDto.getHashCode())
                .orElseThrow(() -> new StudentException("승인하지 않은 키입니다."));
        studentRepository.findByStudentCodeAndName(studentDto.getStudentCode(), studentDto.getName())
                .map(findStudent -> {
                    if (findStudent.getSiteInfo() != null) {
                        throw new StudentException("현재 학생은 정보가 존재합니다.");
                    }
                    return null;
                })
                .orElseGet(() -> {
                    return null;
                });
        Student student = getStudent(studentDto);
        Pattern compile = Pattern.compile("^[a-z]*$");
        Matcher matcher = compile.matcher(siteInfoDto.getDomainName());
        if (!matcher.matches())
            throw new StudentSiteInfoException("domainName은 대문자 , 특수 문자 , 뛰어쓰기가 존재하면 안됩니다.");
        if (student.getIsDeleted()) throw new StudentException("비활성화 계정입니다");
        // TODO 수정 요함 >> 한 사용자가 여러 계정 생성 못하도록 막기
        SiteInfo createSiteInfo = (SiteInfo) siteInfoRespository.findByDomainName(siteInfoDto.getDomainName())
                .map(siteInfo -> {
                    if (siteInfo.getDomainName() != null) {
                        throw new StudentSiteInfoException("존재하는 도메인 입니다.");
                    }
                    return null;
                })
                .orElseGet(() -> SiteInfo.createSiteInfo(siteInfoDto.getDomainName(), "s" + studentDto.getStudentCode()));

        //ssh 계정으로 도메인 및 sftp생성
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        sshConnection.createDomain(createSiteInfo.getDomainName(),studentDto.getPassword());

        //jdbc를 활용하여 데이터 베이스 생성
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.createJDBCMysqlUser(createSiteInfo.getDatabaseName(),studentDto.getPassword());
        siteInfoRespository.save(createSiteInfo);
        student.modifyStudent(studentDto, admin, createSiteInfo);
        return new StudentDao.BasicStudent(student);
    }

    public StudentDao.BasicStudent modifyStudentInfo(Integer studentId, StudentReq.StudentDto studentDto, StudentReq.SiteInfoDto siteInfoDto) {
        Student student = studentRepository.findSiteInfoById(studentId)
                .orElseThrow(() -> new StudentException("존재하지 않는 학생입니다."));
        if (student.getSiteInfo() == null) throw new StudentException("아직 등록하지 않은 계정입니다.");
        if (siteInfoDto.getDomainName() != null || siteInfoDto.getDatabaseName() != null) {
            if (!student.getSiteInfo().getDomainName().equals(siteInfoDto.getOriginDomain()))
                throw new StudentException("기존 도메인을 잘못 입력하셨습니다.");
            if (siteInfoDto.getDomainName().equals(siteInfoDto.getOriginDomain())) {
                throw new StudentSiteInfoException("변경하려는 도메인이 동일합니다.");
            }
            //ssh 접근하여 유저명 , 도메인 정보 수정
            SSHConnection sshConnection = new SSHConnection(utilConfigure);
            sshConnection.modifyStudentInfo(siteInfoDto);
            //siteInfo database 정보 수정
            student.getSiteInfo().modifySiteInfo(siteInfoDto);
        }
        //student database 정보 수정
        student.modifyStudent(studentDto, null, null);
        return new StudentDao.BasicStudent(student);
    }

    private Student getStudent(StudentReq.StudentDto studentDto) {
        return studentRepository.findByStudentCodeAndName(studentDto.getStudentCode(), studentDto.getName())
                .orElseThrow(() -> new StudentException("존재하지 않는 학생입니다."));
    }


}
