package kr.ac.seowon.media.studentadminsite.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.exception.CustomCollectionValidtion;
import kr.ac.seowon.media.studentadminsite.service.AdminStudentObserveService;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/admin/studentinfo")
@RequiredArgsConstructor
@Slf4j
public class AdminStudentObserveAPIController {

    private final UtilConfigure utilConfigure;
    private final AdminStudentObserveService adminStudentObserveService;
    private final CustomCollectionValidtion concurrentInsertStudentsInfo;


    /**
     * 간단한 정렬은 가능하나 완벽하게는??
     * @return
     */
    @GetMapping
    public Res findStudentInfo(Pageable pageable){
        return null;
    }

    /**
     * 검색 조건 정의후에 개발
     * /////////////////////
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
     * @param pageable
     * @return
     */
    @GetMapping("/v1/search")
    public Res searchV1StudentInfo(){
        return null;
    }

    @GetMapping("/v1/search/")
    public Res searchLikeStudentInfo(){
        return null;
    }

    @PutMapping("/student/{studentId}")
    public Res modifyStduentInfo(
            @PathVariable("studentId") Integer studentId,
            AdminObserveReq.AdminModifyStudentDto modifyStudentDto) {
        StudentDao.BasicStudent basicStudent = adminStudentObserveService.modifyStudentInfo(studentId, modifyStudentDto);
        return Res.isOkWithData(basicStudent, "정보 수정 성공");
    }

    @PutMapping("/students/{studentIds}")
    public Res modifyStduentsInfo(
            @PathVariable("studentIds") List<Integer> userIds,
            List<AdminObserveReq.AdminModifyStudentDto> modifyStudentDtos
    ) {
        List<StudentDao.BasicStudent> basicStudents = adminStudentObserveService.modifyStudentsInfo(userIds , modifyStudentDtos);
        return Res.isOkWithData(null, "정보 수정 성공");
    }

    @PostMapping("/insert/{adminId}")
    public Res insertBasicInfo(
            @PathVariable("adminId") Integer adminId,
            @Valid @RequestBody AdminObserveReq.BasicStudentDto basicStudentDto
    ) {
        adminStudentObserveService.insertStudentInfo(adminId, basicStudentDto);
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
        adminStudentObserveService.concurrentInsertStudentsInfo(adminId, basicStudentDtos);
        return Res.isOkByMessage("동시에 학생 정보를 입력하였습니다.");
    }

    //단일 정보 삭제
    @DeleteMapping("/student/{studentId}")
    public Res deleteStudentInfo(@PathVariable("studentId") Integer studentId) {
        adminStudentObserveService.deleteStudentInfo(studentId);
        return Res.isOkByMessage(studentId + " 의 정보를 삭제 하였습니다.");
    }

    //복수 정보 삭제
    @DeleteMapping("/students/{studentIds}")
    public Res deleteStudentInfos(@PathVariable("studentIds") List<Integer> studentIds) {
        adminStudentObserveService.deleteStudentsInfo(studentIds);
        return Res.isOkByMessage("정보 삭제 성공");
    }

}
