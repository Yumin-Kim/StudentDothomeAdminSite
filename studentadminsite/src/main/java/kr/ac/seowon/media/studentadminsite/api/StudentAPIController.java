package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.StudentSiteInfoException;
import kr.ac.seowon.media.studentadminsite.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Slf4j
public class StudentAPIController {

    private final StudentService studentService;

    @GetMapping
    public Res findStudentCodeAndName(@Validated({StudentReq.FindStudentCode.class}) @ModelAttribute StudentReq.StudentDto studentDto) {
        StudentDao.BasicStudent basicStudent = studentService.findStudentCodeAndName(studentDto);
        return Res.isOkWithData(basicStudent, "학번 조회 결과 입니다.");
    }

    @GetMapping("/studentcode")
    public Res findStudentCode(@RequestParam("name") String name, @RequestParam("studentCode") Integer studentCode) {
        StudentDao.BasicStudent basicStudent = studentService.findStudentCode(name, studentCode);
        return Res.isOkWithData(basicStudent, "학번 조회 성공");
    }

    @PostMapping("/login")
    public Res studentLogin(@Validated({StudentReq.FindStudentCode.class}) @RequestBody StudentReq.StudentDto studentDto) {
        StudentDao.BasicStudent basicStudent = studentService.findStudentCodeAndName(studentDto);
        return Res.isOkWithData(basicStudent, "로그인 성공");
    }

    @PostMapping("/logout")
    public Res studentLogout() {
        return Res.isOkByMessage("로그아웃 성공");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Res createStudent(@Validated({StudentReq.CreateStudent.class}) @RequestBody StudentReq.StudentDto studentDto) {
        StudentDao.BasicStudent basicStudent = studentService.createStudent(studentDto);
        return Res.isOkWithData(basicStudent, HttpStatus.CREATED.toString());
    }

    @PutMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public Res modifyStudentInfo(
            @PathVariable("studentId") Integer studentId,
            @RequestBody StudentReq.ModifyStudentDto modifyStudentDto) {
        StudentReq.StudentDto studentDto = new StudentReq.StudentDto(modifyStudentDto);
        StudentReq.SiteInfoDto siteInfoDto = new StudentReq.SiteInfoDto(modifyStudentDto);
        if (siteInfoDto.getDomainName().equals(siteInfoDto.getOriginDomain())) {
            throw new StudentSiteInfoException("변경하려는 도메인이 동일합니다.");
        }
        StudentDao.BasicStudent basicStudent = studentService.modifyStudentInfo(studentId, studentDto,siteInfoDto);
        return Res.isOkWithData(basicStudent, "정보 수정을 완료 했습니다.");
    }


    @PostMapping("/siteinfo")
    public Object modifySiteInfo(@Valid @RequestBody SiteInfo siteInfo) {
//        SshConnection sshConnection = new SshConnection(utilConfigure);
//        sshConnection.modifyStudentInfo(siteInfo);
        return siteInfo;
    }

}
