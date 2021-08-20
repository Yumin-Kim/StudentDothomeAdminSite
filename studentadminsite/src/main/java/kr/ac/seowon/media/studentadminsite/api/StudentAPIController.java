package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.dto.Req;
import kr.ac.seowon.media.studentadminsite.utils.SshConnection;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/student")
public class StudentAPIController {

    @PostMapping("/siteinfo")
    public Object modifySiteInfo(@Valid @RequestBody Req.SiteInfo siteInfo) {
        SshConnection sshConnection = new SshConnection();
        sshConnection.modifyStudentInfo(siteInfo);
        return siteInfo;
    }

}
