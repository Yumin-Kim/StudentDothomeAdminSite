package kr.ac.seowon.media.studentadminsite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.AdminObserveException;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.InsertDuplicateException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminStudentObserveService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final SiteInfoRespository siteInfoRepository;
    private final UtilConfigure utilConfigure;

    public void insertStudentInfo(Integer adminId, AdminObserveReq.BasicStudentDto basicStudentDto) {
        Admin observeAdmin = getAdmin(adminId);
        studentRepository.findByStudentCodeAndName(basicStudentDto.getStudentCode(), basicStudentDto.getName())
                .map(student -> {
                    if (student != null) {
                        throw new AdminObserveException("존재하는 학번 저장을 시도하였습니다.");
                    }
                    return null;
                });
        Student student = Student.createStudent(basicStudentDto,observeAdmin);
        studentRepository.save(student);
    }

    public void concurrentInsertStudentsInfo(Integer adminId, List<AdminObserveReq.BasicStudentDto> basicStudentDtos) throws JsonProcessingException {
        Admin observeAdmin = getAdmin(adminId);
        List<Integer> studentCodes = basicStudentDtos.stream()
                .map(AdminObserveReq.BasicStudentDto::getStudentCode)
                .collect(toList());
        Set<Integer> hashstudentCodes = basicStudentDtos.stream()
                .map(AdminObserveReq.BasicStudentDto::getStudentCode)
                .collect(toSet());
        if(studentCodes.size() != hashstudentCodes.size()) throw new AdminObserveException("중복 되는 학번이 존재합니다");
        List<Student> findByStudentCodes = studentRepository.findByStudentCodeIn(studentCodes);
        if (findByStudentCodes.size() != 0) {
            List<Integer> duplicateStudents = findByStudentCodes.stream()
                    .map(Student::getStudentCode)
                    .collect(toList());
            ObjectMapper objectMapper = new ObjectMapper();
            String convertJsonData = objectMapper.writeValueAsString(duplicateStudents);
            throw new InsertDuplicateException(convertJsonData);
        }else{
            basicStudentDtos.stream()
                    .map(basicStudentDto -> Student.createStudent(basicStudentDto, observeAdmin))
                    .forEach(student -> studentRepository.save(student));
        }

    }

    /**
     *학생의 db엦 저장된 정보는 남겨두면 도메인 또는 dateBase 삭제
     * siteInfo 정보 삭제 및 student isdeleted 값 수정
     * 추가사항 기존 데이터 베이스의 정보를 삭제 할건지?
     * @param studentId
     */
    public void deleteStudentInfo(Integer studentId) {
        Student student = getStudent(studentId);
        //database 정보 수정 및 삭제
        utils_studentInfoDelete(student);

        siteInfoRepository.delete(student.getSiteInfo());
        student.disabledStudent(true);
    }

    /**
     * 입력한 학생 중 존재하지 않는 경우
     * @param studentIds
     */
    public void deleteStudentsInfo(List<Integer> studentIds) {
        List<Student> findByStudentList = studentRepository.findByIdIn(studentIds);
        if (findByStudentList.size() != studentIds.size()) {
            throw new StudentException("입력한 학생중 사이트가 존재하지 않은 학생이 있습니다.");
        }
        findByStudentList
                .forEach(student ->  {
                    siteInfoRepository.delete(student.getSiteInfo());
                    student.disabledStudent(true);
                    utils_studentInfoDelete(student);
                });
    }

    public StudentDao.BasicStudent modifyStudentInfo(Integer userId, AdminObserveReq.AdminModifyStudentDto modifyStudentDto) {
        Student student = getStudent(userId);
        student.adminPermitModifyStudent(modifyStudentDto);
        return new StudentDao.BasicStudent(student);
    }

    private Student getStudent(Integer studentId) {
        Student student = studentRepository.findSiteInfoById(studentId)
                .orElseThrow(() -> new StudentException("사이트가 존재하지 않는 학생입니다."));
        return student;
    }

    private Admin getAdmin(Integer adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new AdminObserveException("존재하지 않는 관리자 입니다"));
    }

    private void utils_studentInfoDelete(Student student) {
        //데이터 베이스 삭제
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.deleteDatabase(student.getSiteInfo().getDatabaseName());

        //유저 삭제
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        sshConnection.deleteDomainInfo(student.getSiteInfo().getDomainName());
    }
}
