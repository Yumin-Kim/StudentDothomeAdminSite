package kr.ac.seowon.media.studentadminsite.repository.was;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.DeployMethod;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.IntegratedErrorLog;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.LocalWasInfo;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.WASItem;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.repository.admin.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.student.StudentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IntegratedErrorLogRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private IntegratedErrorLogRepository integratedErrorLogRepository;
    @Autowired
    private LocalWasInfoRepository localWasInfoRepository;

    @BeforeEach
    @DisplayName("정보 생성")
    void configure() {
        AdminObserveReq.BasicStudentDto basicStudentDto1 = new AdminObserveReq.BasicStudentDto("김유민", 201510302);
        Admin admin = Admin.createAdmin("name1", "1234", "asd", "asd");
        Student student1 = Student.createStudent(basicStudentDto1, admin);
        adminRepository.save(admin);
        Student a = studentRepository.save(student1);
        final LocalWasInfo localWasInfo = LocalWasInfo.createEntitiy(student1, 1010, WASItem.NODEJS, DeployMethod.GIT, "test", "test");
        final LocalWasInfo save = localWasInfoRepository.save(localWasInfo);
        final IntegratedErrorLog error_log = IntegratedErrorLog.createEntity("Error Log", student1);
        error_log.updateLocalWasLog(save);
        integratedErrorLogRepository.save(error_log);
    }

    @Test
    @DisplayName("")
    void IntegratedErrorLogRepositoryTest() throws Exception{
        // given
        integratedErrorLogRepository.findJoinWasId(1).orElseThrow(() -> new IllegalStateException("존재하지 않습니다"));
        // when

        // then
    }

}