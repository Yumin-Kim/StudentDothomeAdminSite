package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.MysqlUserBean;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/util")
@RequiredArgsConstructor
public class AdminUtilPermitController {

    private final UtilConfigure utilConfigure;

    @GetMapping("/db")
    public Res getDatabasePermit() {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        List<MysqlUserBean> hostingUserNames = jdbcRootPermition.selectDatabase();
        return Res.isOkWithData(hostingUserNames, "Database 정보 조회 성공");
    }

    @GetMapping("/domain")
    public Res getDomainPermit() {
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        List<String> sshUsernames = sshConnection.selectSSHDomainInfo();
        return Res.isOkWithData(sshUsernames, "domain정보 조회 성공");
    }

    @PostMapping("/db")
    public Res createDatabasePermit(
            @RequestParam("database") String database,
            @RequestParam("password") String password) {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.createJDBCMysqlUser(database, password);
        return Res.isOkByMessage(database + " : 데이터 베이스 생성 " + password + " : 비밀번호 입니다. ");
    }

    @PostMapping("/domain")
    public Res createDomainPermit(@RequestParam("domain") String domain, @RequestParam("password") String password) {
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
        sshConnection.createDomain(domain, password);
        return Res.isOkByMessage(domain + ": 도메인을 생성");
    }

    //일괄 삭제 하는 기능 으로 변경
    @DeleteMapping("/db")
    public Res deleteDatabasePermit(@RequestParam("database") List<String> databases) {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        databases.stream()
                        .forEach(database->jdbcRootPermition.deleteDatabase(database));
        return Res.isOkByMessage("Database 정보 삭제 성공");
    }

    //일괄 삭제 하는 기능 으로 변경
    @DeleteMapping("/domain")
    public Res deleteDomainPermit(@RequestParam("domain") List<String> domains) {
        SSHConnection sshConnection = new SSHConnection(utilConfigure);
//        System.out.println(" = " + String.join(" ",domains));
                sshConnection.deleteDomainInfo(String.join(" ",domains));
        return Res.isOkByMessage("domain 정보 삭제 성공");
    }

}
