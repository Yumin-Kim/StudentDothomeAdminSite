package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.dao.StudentDao;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentAPIController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Res createStudent(@Valid @RequestBody StudentReq.StudentDto studentDto) {
        StudentDao.BasicStudent basicStudent = studentService.createStudent(studentDto);
        return Res.isOkWithData(basicStudent, HttpStatus.CREATED.toString());
    }


    @PostMapping("/siteinfo")
    public Object modifySiteInfo(@Valid @RequestBody SiteInfo siteInfo) {
//        SshConnection sshConnection = new SshConnection(utilConfigure);
//        sshConnection.modifyStudentInfo(siteInfo);
        return siteInfo;
    }

}
