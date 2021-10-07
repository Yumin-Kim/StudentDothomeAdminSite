package kr.ac.seowon.media.studentadminsite.service.adminobserve;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.dao.AdminObserveDao;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

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

    public StudentDao.StudentSiteInfo findSelectStudentSiteInfo(Integer studentId) {
        Student student = getStudent(studentId);
        if (student.getSiteInfo() != null) {
            return new StudentDao.StudentSiteInfo(student.getSiteInfo());
        }
        else{
            return null;
        }
    }


    public AdminObserveDao.FullInfo findAllStudentInfo(Pageable pageable) {
        Page<Student> findAllStudentInfo = studentRepository.findAll(pageable);
        return getFullInfo(findAllStudentInfo);
    }

    public AdminObserveDao.FullInfo searchStudentInfV1(Boolean onChange, AdminObserveReq.SearchCondition searchCondition, Pageable pageable) {
        Page<Student> findByConditionToStudentInfo = null;
        if (onChange) {
            findByConditionToStudentInfo = studentRepository.searchEqualsConditionInfoV1(pageable, searchCondition);

        } else {
            findByConditionToStudentInfo = studentRepository.searchSimilarConditionInfoV1(pageable, searchCondition);
        }
        return getFullInfo(findByConditionToStudentInfo);
    }

    private AdminObserveDao.FullInfo getFullInfo(Page<Student> findByConditionToStudentInfo) {
        List<AdminObserveDao.AdminObserveStudentInfo> mappingData = findByConditionToStudentInfo.getContent().stream()
                .map(student -> new AdminObserveDao.AdminObserveStudentInfo(
                        student,
                        student.getSiteInfo(),
                        student.getAdmin()))
                .collect(toList());
        return new AdminObserveDao.FullInfo(mappingData,
                findByConditionToStudentInfo.getNumber(),
                findByConditionToStudentInfo.getTotalPages(),
                findByConditionToStudentInfo.getSize(),
                (int) findByConditionToStudentInfo.getTotalElements());
    }

    private Student getStudent(Integer studentId) {
        Student student = studentRepository.findSiteInfoById(studentId)
                .orElseThrow(() -> new StudentException("사이트가 존재하지 않는 학생입니다."));
        return student;
    }


}
