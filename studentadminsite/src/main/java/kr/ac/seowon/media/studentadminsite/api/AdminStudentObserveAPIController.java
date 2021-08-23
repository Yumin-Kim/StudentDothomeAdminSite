package kr.ac.seowon.media.studentadminsite.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.exception.CustomCollectionValidtion;
import kr.ac.seowon.media.studentadminsite.service.AdminStudentObserveService;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.SshConnection;
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

    @PostMapping("/insert")
    public Res insertBasicInfo(@Valid @RequestBody AdminObserveReq.BasicStudentDto basicStudentDto){
        adminStudentObserveService.insertStudentInfo(basicStudentDto);
        return Res.isOkByMessage("학생정보를 저장하였습니다.");
    }

    @PostMapping("/concurrentinsert")
    public Res coucurrentInsertBasicInfo(
            @Valid @RequestBody List<AdminObserveReq.BasicStudentDto> basicStudentDtos,
            BindingResult bindingResult) throws BindException, JsonProcessingException {
        concurrentInsertStudentsInfo.validate(basicStudentDtos, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        adminStudentObserveService.concurrentInsertStudentsInfo(basicStudentDtos);
        return Res.isOkByMessage("동시에 학생 정보를 입력하였습니다.");
    }


    //TODO deleteStudentInfo Response 데이터 수정
    @DeleteMapping
    public String deleteStudentInfo(@RequestParam("databasename") String databasename) {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.deleteDatabase(databasename);
        return "success";
    }

    @GetMapping
    public String test(@RequestParam("domainname") String domainName) {
        SshConnection sshConnection = new SshConnection(utilConfigure);
        sshConnection.deleteDomainInfo(domainName);
        return "test";
    }

//    @DeleteMapping("/lists")
//    public String deleteStudentInfo( String databasename){
//        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition();
//        jdbcRootPermition.deleteDatabase(databasename);
//        return "success";
//    }

}
