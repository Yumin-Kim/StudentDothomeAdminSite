package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.SessionFactory;
import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Slf4j
public class StudentAPIController {

    private final StudentService studentService;
    private final SessionFactory sessionFactory;

    @GetMapping
    public Res findStudentCodeAndName(
            HttpServletRequest request,
            @Validated({StudentReq.FindStudentCode.class}) @ModelAttribute StudentReq.StudentDto studentDto) {
        sessionFactory.validationSession(request,"student");
        StudentDao.BasicStudent basicStudent = studentService.findStudentCodeAndName(studentDto);
        return Res.isOkWithData(basicStudent, "학번 조회 결과 입니다.");
    }

    @GetMapping("/studentcode")
    public Res findStudentCode(@RequestParam("name") String name, @RequestParam("studentCode") Integer studentCode) {
        StudentDao.DefaultStudent basicStudent = studentService.findStudentCode(name, studentCode);
        return Res.isOkWithData(basicStudent, "학번 조회 성공");
    }

    @PostMapping("/login")
    public Res studentLogin(HttpServletRequest request, @Validated({StudentReq.FindStudentCode.class}) @RequestBody StudentReq.StudentDto studentDto) {
        sessionFactory.makeSession(request,"student",studentDto);
        StudentDao.BasicStudent basicStudent = studentService.findStudentCodeAndName(studentDto);
        return Res.isOkWithData(basicStudent, "로그인 성공");
    }

    @PostMapping("/logout")
    public Res studentLogout(
            HttpServletRequest request
    ) {
        sessionFactory.removeSession(request,"student");
        return Res.isOkByMessage("로그아웃 성공");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Res createStudent(@Validated({StudentReq.CreateStudent.class}) @RequestBody StudentReq.AllStudentDto allStudentDto) {
        StudentReq.StudentDto studentDto = new StudentReq.StudentDto(allStudentDto);
        StudentReq.SiteInfoDto siteInfoDto = new StudentReq.SiteInfoDto(allStudentDto);

        StudentDao.BasicStudent basicStudent = studentService.createStudent(studentDto,siteInfoDto);
        return Res.isOkWithData(basicStudent, "계정 생성 성공");
    }

    @PutMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public Res modifyStudentInfo(
            HttpServletRequest request,
            @PathVariable("studentId") Integer studentId,
            @RequestBody StudentReq.ModifyStudentDto modifyStudentDto) {
        StudentReq.StudentDto studentDto = new StudentReq.StudentDto(modifyStudentDto);
        StudentReq.SiteInfoDto siteInfoDto = new StudentReq.SiteInfoDto(modifyStudentDto);

        StudentDao.BasicStudent basicStudent = studentService.modifyStudentInfo(studentId, studentDto,siteInfoDto);
        return Res.isOkWithData(basicStudent, "정보 수정을 완료 했습니다.");
    }


}
