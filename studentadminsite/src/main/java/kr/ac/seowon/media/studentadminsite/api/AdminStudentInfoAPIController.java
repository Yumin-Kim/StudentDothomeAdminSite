package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.exception.UtilJdbcConnectionException;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.SshConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/admin/studentinfo")
@RequiredArgsConstructor
@Slf4j
public class AdminStudentInfoAPIController {

    private final UtilConfigure utilConfigure;

    //TODO deleteStudentInfo Response 데이터 수정
    @DeleteMapping
    public String deleteStudentInfo(@RequestParam("databasename") String databasename){
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
