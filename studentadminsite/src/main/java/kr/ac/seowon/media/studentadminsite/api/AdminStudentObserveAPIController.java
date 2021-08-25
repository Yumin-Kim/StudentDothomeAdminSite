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
import java.util.List;

@RestController
@RequestMapping("/api/admin/studentinfo")
@RequiredArgsConstructor
@Slf4j
public class AdminStudentObserveAPIController {

    private final UtilConfigure utilConfigure;
    private final AdminStudentObserveService adminStudentObserveService;
    private final CustomCollectionValidtion concurrentInsertStudentsInfo;


    @PutMapping("/student/{userId}")
    public Res modifyStduentInfo(
            @PathVariable("userId") Integer userId,
            AdminObserveReq.AdminModifyStudentDto modifyStudentDto){
        StudentDao.BasicStudent  basicStudent= adminStudentObserveService.modifyStudentInfo(userId,modifyStudentDto);
        return Res.isOkWithData(basicStudent, "정보 수정 성공");
    }

    @PutMapping("/students/{userIds}")
    public  Res modifyStduentsInfo(){
        return Res.isOkWithData(null, "정보 수정 성공");
    }

    @PostMapping("/insert/{adminId}")
    public Res insertBasicInfo(
            @PathVariable("adminId") Integer adminId,
            @Valid @RequestBody AdminObserveReq.BasicStudentDto basicStudentDto
    ){
        adminStudentObserveService.insertStudentInfo(adminId,basicStudentDto);
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
        adminStudentObserveService.concurrentInsertStudentsInfo(adminId,basicStudentDtos);
        return Res.isOkByMessage("동시에 학생 정보를 입력하였습니다.");
    }



    //단일 정보 삭제
    @DeleteMapping("/student/{studentId}")
    public Res deleteStudentInfo(@PathVariable("studentId") Integer studentId) {
        adminStudentObserveService.deleteStudentInfo(studentId);
        return Res.isOkByMessage(studentId+ " 의 정보를 삭제 하였습니다.");
    }
    //복수 정보 삭제
    @DeleteMapping("/students/{studentIds}")
    public Res deleteStudentInfos(@PathVariable("studentIds") List<Integer> studentIds) {
        adminStudentObserveService.deleteStudentsInfo(studentIds);
        return Res.isOkByMessage("정보 삭제 성공");
    }

}
