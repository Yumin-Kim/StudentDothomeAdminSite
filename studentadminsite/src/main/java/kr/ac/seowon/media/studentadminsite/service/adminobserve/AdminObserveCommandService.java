package kr.ac.seowon.media.studentadminsite.service.adminobserve;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.AdminObserveException;
import kr.ac.seowon.media.studentadminsite.exception.controllerexception.InsertDuplicateException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.admin.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.student.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.student.StudentRepository;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminObserveCommandService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final UtilConfigure utilConfigure;
    private final SiteInfoRespository siteInfoRepository;

    public void insertStudentInfo(Integer adminId, AdminObserveReq.BasicStudentDto basicStudentDto) {
        Admin observeAdmin = getAdmin(adminId);
        studentRepository.findByStudentCode(basicStudentDto.getStudentCode())
                .map(student -> {
                    if (student != null) {
                        throw new AdminObserveException(ErrorCode.STUDENT_HAS_DATA);
                    }
                    return null;
                });
        Student student = Student.createStudent(basicStudentDto, observeAdmin);
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

        if (studentCodes.size() != hashstudentCodes.size()) throw new AdminObserveException("?????? ?????? ????????? ???????????????");

        List<Student> findByStudentCodes = studentRepository.findByStudentCodeIn(studentCodes);

        if (findByStudentCodes.size() != 0) {
            List<Integer> duplicateStudents = findByStudentCodes.stream()
                    .map(Student::getStudentCode)
                    .collect(toList());
            ObjectMapper objectMapper = new ObjectMapper();
            String convertJsonData = objectMapper.writeValueAsString(duplicateStudents);
            throw new InsertDuplicateException(convertJsonData);
        } else {
            basicStudentDtos.stream()
                    .map(basicStudentDto -> Student.createStudent(basicStudentDto, observeAdmin))
                    .forEach(student -> studentRepository.save(student));
        }

    }

    /**
     * ????????? db??? ????????? ????????? ???????????? ????????? ?????? dateBase ??????
     * siteInfo ?????? ?????? ??? student isdeleted ??? ??????
     * ???????????? ?????? ????????? ???????????? ????????? ?????? ??????????
     * @param studentId
     */
    public void deleteStudentInfo(Integer studentId) {
        Student student = getStudent(studentId);
        //database ?????? ?????? ??? ??????
        utils_studentInfoDelete(student);
        siteInfoRepository.delete(student.getSiteInfo());
        student.disabledStudent(true);
    }

    public List<AdminObserveDtoRes.AdminObserveStudentInfo> deleteStudentsInfo(List<Integer> studentIds) {
        List<Student> findByStudentList = studentRepository.findByIdIn(studentIds);
        if (findByStudentList.size() != studentIds.size()) {
            throw new StudentException(ErrorCode.ADMIN_NOT_HAS_SITEINFO);
        }
        List<Integer> emptySiteInfoStudents = findByStudentList.stream()
                .filter(student -> student.getSiteInfo() == null)
                .map(Student::getStudentCode)
                .collect(toList());
        if(emptySiteInfoStudents.size() != 0){
            throw new StudentException("????????? ????????? " + StringUtils.join(emptySiteInfoStudents, ",") + " ??? ????????? ????????? ????????????.");
        }
        for (Student student : findByStudentList) {
            utils_studentInfoDelete(student);
            siteInfoRepository.delete(student.getSiteInfo());
            student.disabledStudent(true);
        }
        return findByStudentList.stream()
                .map(student -> new AdminObserveDtoRes.AdminObserveStudentInfo(student, student.getSiteInfo(), student.getAdmin()))
                .collect(toList());
    }

    public StudentDtoRes.BasicStudent modifyStudentInfo(AdminObserveReq.AdminModifyStudentDto modifyStudentDto) {
        Student student = getStudent(modifyStudentDto.getId());
        student.adminPermitModifyStudent(modifyStudentDto);
        return new StudentDtoRes.BasicStudent(student);
    }

    public List<StudentDtoRes.BasicStudent> modifyStudentsInfo(List<AdminObserveReq.AdminModifyStudentDto> modifyStudentDtos) {
        List<Integer> studentIds = modifyStudentDtos.stream()
                .map(AdminObserveReq.AdminModifyStudentDto::getId)
                .collect(toList());
        List<Student> findStudents = studentRepository.findByIdIn(studentIds);
        if (findStudents.size() != studentIds.size()) {
            throw new AdminObserveException(ErrorCode.ADMIN_NOT_FOUND_STUDENT);
        }
        for (Student student : findStudents) {
            for (AdminObserveReq.AdminModifyStudentDto modifyStudentDto : modifyStudentDtos) {
                if (student.getId() == modifyStudentDto.getId()) {
                    student.adminPermitModifyStudent(modifyStudentDto);
                }
            }
        }
        return findStudents.stream()
                .map(StudentDtoRes.BasicStudent::new)
                .collect(toList());
    }


    private Student getStudent(Integer studentId) {
        Student student = studentRepository.findSiteInfoById(studentId)
                .orElseThrow(() -> new StudentException(ErrorCode.ADMIN_NOT_HAS_SITEINFO));
        return student;
    }

    private Admin getAdmin(Integer adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new AdminObserveException("???????????? ?????? ????????? ?????????"));
    }

    private void utils_studentInfoDelete(Student student) {
        //????????? ????????? ??????
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.deleteDatabase(student.getSiteInfo().getDatabaseName());

        //?????? ??????
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        sshConnection.deleteDomainInfo(student.getSiteInfo().getDomainName());
    }





}
