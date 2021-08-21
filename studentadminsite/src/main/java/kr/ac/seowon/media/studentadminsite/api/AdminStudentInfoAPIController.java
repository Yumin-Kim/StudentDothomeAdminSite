package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.utils.JdbcRootPermition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/studentinfo")
@Slf4j
public class AdminStudentInfoAPIController {

    @DeleteMapping
    public String deleteStudentInfo(@RequestParam("databasename") String databasename){
        log.info("@RequestParam(\"databasename\") ={}",databasename);
        JdbcRootPermition jdbcRootPermition = new JdbcRootPermition();
        jdbcRootPermition.deleteDatabase(databasename);
        return "success";
    }

}
