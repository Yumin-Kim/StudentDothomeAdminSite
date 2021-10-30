package kr.ac.seowon.media.studentadminsite.service.adminobserve;

import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.admin.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.student.SiteInfoRespository;
import kr.ac.seowon.media.studentadminsite.repository.student.StudentRepository;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminStudentObserveService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final SiteInfoRespository siteInfoRepository;
    private final UtilConfigure utilConfigure;

    public StudentDtoRes.StudentSiteInfo findSelectStudentSiteInfo(Integer studentId) {
        Student student = getStudent(studentId);
        if (student.getSiteInfo() != null) {
            return new StudentDtoRes.StudentSiteInfo(student.getSiteInfo());
        }
        else{
            return null;
        }
    }


    public AdminObserveDtoRes.FullInfo findAllStudentInfo(Pageable pageable) {
        Page<Student> findAllStudentInfo = studentRepository.findAll(pageable);
        return getFullInfo(findAllStudentInfo);
    }

    public AdminObserveDtoRes.FullInfo searchStudentInfV1(Boolean onChange, AdminObserveReq.SearchCondition searchCondition, Pageable pageable) {
        Page<Student> findByConditionToStudentInfo = null;
        if (onChange) {
            findByConditionToStudentInfo = studentRepository.searchEqualsConditionInfoV1(pageable, searchCondition);

        } else {
            findByConditionToStudentInfo = studentRepository.searchSimilarConditionInfoV1(pageable, searchCondition);
        }
        return getFullInfo(findByConditionToStudentInfo);
    }

    private AdminObserveDtoRes.FullInfo getFullInfo(Page<Student> findByConditionToStudentInfo) {
        List<AdminObserveDtoRes.AdminObserveStudentInfo> mappingData = findByConditionToStudentInfo.getContent().stream()
                .map(student -> new AdminObserveDtoRes.AdminObserveStudentInfo(
                        student,
                        student.getSiteInfo(),
                        student.getAdmin()))
                .collect(toList());
        return new AdminObserveDtoRes.FullInfo(mappingData,
                findByConditionToStudentInfo.getNumber(),
                findByConditionToStudentInfo.getTotalPages(),
                findByConditionToStudentInfo.getSize(),
                (int) findByConditionToStudentInfo.getTotalElements());
    }

    private Student getStudent(Integer studentId) {
        Student student = studentRepository.findSiteInfoById(studentId)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_SITEINFO));
        return student;
    }


}
