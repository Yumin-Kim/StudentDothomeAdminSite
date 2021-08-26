package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/util")
@RequiredArgsConstructor
public class AdminUtilPermitController {

    private final UtilConfigure utilConfigure;

    @PostMapping("/db")
    public Res createDatabasePermit(
            @RequestParam("database") String database,
            @RequestParam("password") String password) {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.createJDBCMysqlUser(database,password);
        return Res.isOkByMessage(database+" : 데이터 베이스 생성 "+ password +" : 비밀번호 입니다. ");
    }

    @PostMapping("/domain")
    public Res createDomainPermit(@RequestParam("domain") String domain , @RequestParam("password") String password) {
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        sshConnection.createDomain(domain,password);
        return Res.isOkByMessage(domain + ": 도메인을 생성");
    }

    @DeleteMapping("/db")
    public Res deleteDatabasePermit(@RequestParam("database") String database) {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.deleteDatabase(database);
        return Res.isOkByMessage("Database 정보 삭제 성공");
    }

    @DeleteMapping("/domain")
    public Res deleteDomainPermit(@RequestParam("domain") String domain) {
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        sshConnection.deleteDomainInfo(domain);
        return Res.isOkByMessage("domain 정보 삭제 성공");
    }

}
