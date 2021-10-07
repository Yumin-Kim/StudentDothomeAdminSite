package kr.ac.seowon.media.studentadminsite.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.ac.seowon.media.studentadminsite.SessionFactory;
import kr.ac.seowon.media.studentadminsite.dao.AdminObserveDao;
import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.exception.CustomCollectionValidtion;
import kr.ac.seowon.media.studentadminsite.service.adminobserve.AdminObserveCommandService;
import kr.ac.seowon.media.studentadminsite.service.adminobserve.AdminStudentObserveService;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/admin/studentinfo")
@RequiredArgsConstructor
@Slf4j
public class AdminStudentObserveAPIController {

    private final UtilConfigure utilConfigure;
    private final AdminStudentObserveService adminStudentObserveService;
    private final CustomCollectionValidtion concurrentInsertStudentsInfo;
    private final AdminObserveCommandService adminObserveCommandService;
    private final SessionFactory sessionFactory;

    /**
     * 간단한 정렬은 가능하나 완벽하게는??
     *
     * @return
     */
    @GetMapping
    public Res findStudentInfo(HttpServletRequest request, Pageable pageable
    ) {
        AdminObserveDao.FullInfo fullInfo = adminStudentObserveService.findAllStudentInfo(pageable);
        return Res.isOkWithData(fullInfo, "학생 정보 조회 성공");
    }

    @GetMapping("/site/{studentId}")
    public Res findSelectStudentSiteInfo(
            HttpServletRequest request,
            @PathVariable("studentId") Integer studentId) {
        StudentDao.StudentSiteInfo siteInfo = adminStudentObserveService.findSelectStudentSiteInfo(studentId);
        return Res.isOkWithData(siteInfo, "학생 사이트 조회 성공");
    }

    /**
     * 검색 조건 정의후에 개발
     * /////////////////////
     * search 버튼 클릭시
     * 검색 조건 : 이름 도메인 데이터 베이스 휴학여부 비활성화 관리자 학번 전화번호
     * 조건 : 완벽하게 일치 시 아니면 like 사용해서 진행할지
     * 조건 : 정렬 기준 ASC,DESC
     * 조건 : 페이징을 조건 사용할 것인지
     * /////////////////////
     * 완벽한 일치할 시
     * 단건 : 도메인 , 데이터 베이스 , 학번 , 전화번호
     * 복수 건 : 이름 , 휴학여부 , 비활성화 , 관리자
     * like 사용하는 경우
     * 복수 건 : 이름 , 관리자 , 도메인 , 데이터 베이스 , 학번 , 전화번호
     * 완벽한 일치시만 가능
     * 휴학여부 , 비활성화
     */
    @PostMapping("/v1/search")
    public Res searchV1StudentInfo(
            @RequestParam("onChange") @NotNull(message = "공백") Boolean onChange,
            @RequestBody AdminObserveReq.SearchCondition searchCondition,
            HttpServletRequest request,
            Pageable pageable
    ) throws BindException {
//        log.info("{}",onChange);
//        if (bindingResult.hasErrors()) {
//            FieldError fieldError = new FieldError("onChange", "onChange", "onChange 값을 입력해주세요");
//            bindingResult.addError(fieldError);
//            throw new BindException(bindingResult);
//        }
        AdminObserveDao.FullInfo fullInfo = adminStudentObserveService.searchStudentInfV1(onChange, searchCondition, pageable);
        return Res.isOkWithData(fullInfo, "조회 성공");
    }


    @PutMapping("/student")
    public Res modifyStduentInfo(
            HttpServletRequest request,
            @Valid AdminObserveReq.AdminModifyStudentDto modifyStudentDto) {
        StudentDao.BasicStudent basicStudent = adminObserveCommandService.modifyStudentInfo(modifyStudentDto);
        return Res.isOkWithData(basicStudent, "정보 수정 성공");
    }

    @PutMapping("/students")
    public Res modifyStduentsInfo(
            HttpServletRequest request,
            @Valid @RequestBody List<AdminObserveReq.AdminModifyStudentDto> modifyStudentDtos,
            BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        List<StudentDao.BasicStudent> basicStudents = adminObserveCommandService.modifyStudentsInfo(modifyStudentDtos);
        return Res.isOkWithData(basicStudents, "정보 수정 성공");
    }
    //TODO 요청 받는 값 변경

    @PostMapping("/insert/{adminId}")
    public Res insertBasicInfo(
            HttpServletRequest request,
            @PathVariable("adminId") Integer adminId,
            @Valid @RequestBody AdminObserveReq.BasicStudentDto basicStudentDto
    ) {
        adminObserveCommandService.insertStudentInfo(adminId, basicStudentDto);
        return Res.isOkByMessage("학생정보를 저장하였습니다.");
    }

    @PostMapping("/concurrentinsert/{adminId}")
    public Res coucurrentInsertBasicInfo(
            @PathVariable("adminId") Integer adminId,
            @Valid @RequestBody List<AdminObserveReq.BasicStudentDto> basicStudentDtos,
            BindingResult bindingResult) throws BindException, JsonProcessingException {
        concurrentInsertStudentsInfo.validate(basicStudentDtos, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        adminObserveCommandService.concurrentInsertStudentsInfo(adminId, basicStudentDtos);
        return Res.isOkByMessage("동시에 학생 정보를 입력하였습니다.");
    }

    //단일 정보 삭제
    @Deprecated
    @DeleteMapping("/student/{studentId}")
    public Res deleteStudentInfo(@PathVariable("studentId") Integer studentId) {
        adminObserveCommandService.deleteStudentInfo(studentId);
        return Res.isOkByMessage(studentId + " 의 정보를 삭제 하였습니다.");
    }

    //복수 정보 삭제
    @DeleteMapping("/students/{studentIds}")
    public Res deleteStudentInfos(@PathVariable("studentIds") List<Integer> studentIds) {
        List<AdminObserveDao.AdminObserveStudentInfo>  adminObserveStudentInfos = adminObserveCommandService.deleteStudentsInfo(studentIds);
        return Res.isOkWithData(adminObserveStudentInfos,"정보 삭제 성공");
    }

}
