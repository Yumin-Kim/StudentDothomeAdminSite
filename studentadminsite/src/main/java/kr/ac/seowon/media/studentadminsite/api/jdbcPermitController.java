package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db")
@RequiredArgsConstructor
public class jdbcPermitController {

    private final UtilConfigure utilConfigure;

    @DeleteMapping
    public String deletePermit(@RequestParam("database") String database) {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.deleteDatabase(database);
        return "sucess";
    }

    @GetMapping
    public String create(
            @RequestParam("database") String database,
            @RequestParam("password") String password) {
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition(utilConfigure);
        jdbcRootPermition.createJDBCMysqlUser(database,password);
        return "sucess";
    }
    
}
