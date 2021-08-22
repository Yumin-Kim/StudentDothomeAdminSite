package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.dto.Req;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/student")
@Slf4j
public class StudentAPIController {


    @PostMapping("/siteinfo")
    public Object modifySiteInfo(@Valid @RequestBody Req.SiteInfo siteInfo) {
//        SshConnection sshConnection = new SshConnection(utilConfigure);
//        sshConnection.modifyStudentInfo(siteInfo);
        return siteInfo;
    }

}
