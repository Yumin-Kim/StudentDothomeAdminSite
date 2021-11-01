package kr.ac.seowon.media.studentadminsite.service.was;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.DeployMethod;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.IntegratedErrorLog;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.LocalWasInfo;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.WASItem;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentReq;
import kr.ac.seowon.media.studentadminsite.dto.was.UserDatabaseErrorLog;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoReq;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoRes;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.LocalWasException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.student.StudentRepository;
import kr.ac.seowon.media.studentadminsite.repository.was.IntegratedErrorLogRepository;
import kr.ac.seowon.media.studentadminsite.repository.was.LocalWasInfoRepository;
import kr.ac.seowon.media.studentadminsite.utils.SSHConnection;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import kr.ac.seowon.media.studentadminsite.utils.was.UserDataBaseJDBC;
import lombok.RequiredArgsConstructor;
import org.hibernate.secure.spi.IntegrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Transactional
public class LocalWasCommandService {

    private final IntegratedErrorLogRepository integratedErrorLogRepository;
    private final LocalWasInfoRepository localWasInfoRepository;
    private final UserDataBaseJDBC userDataBaseJDBC;
    private final StudentRepository studentRepository;
    private final UtilConfigure utilConfigure;
    private SSHConnection sshConnection;

    //중복 검색
    private void validationLocalWasInfo(Integer port){
        localWasInfoRepository.findByPort(port)
                .map(localWasInfo -> {
                    if (localWasInfo.getHasDataBase() == null)
                        throw new LocalWasException(ErrorCode.LOCAL_DUPLICATE_PORT);
                    return null;
                })
                .orElseGet(() -> false);
    }

    /**
     * 테스트 진행전 아무런 옵션없이 was 동작 테스트 진행
     * @param localTestDto
     * @param studentInfo
     * @return
     */

    public WasInfoRes.LocalWasTestResultRes connectTestWasNotOption(WasInfoReq.LocalTestDto localTestDto, StudentDtoRes.StudentFullDto studentInfo) {
        validationLocalWasInfo(localTestDto.getPort());
        WasInfoRes.LocalWasTestResultRes result= null;
        if (localTestDto.getName().equals(WASItem.NODEJS)) {
            final Student student = studentRepository.findByStudentCodeAndName(studentInfo.getStudentCode(), studentInfo.getName())
                    .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
            LocalWasInfo localWasInfo = null;
            switch (localTestDto.getDeployMethod()) {
                case GIT:
                    localWasInfo = LocalWasInfo.createEntitiy(student, localTestDto.getPort(),localTestDto.getName(), localTestDto.getDeployMethod(), localTestDto.getApplicationName(),  localTestDto.getDirLocation(), localTestDto.getGithubLink());
                    break;
                case HTTP:
                    //파일 업로드 동작할 수 있게 로직 필요
                    localWasInfo = LocalWasInfo.createEntitiy(student, localTestDto.getPort(),localTestDto.getName(),localTestDto.getDeployMethod(),localTestDto.getApplicationName(),localTestDto.getDirLocation());
                    break;
                case SFTP:
                    localWasInfo = LocalWasInfo.createEntitiy(student, localTestDto.getPort(),localTestDto.getName(),localTestDto.getDeployMethod(),localTestDto.getApplicationName(),localTestDto.getDirLocation());
                    break;
            }
            String userDatabaseUrl = "jdbc:mysql://localhost:3306/"+student.getSiteInfo().getDatabaseName();
            LocalWasInfo saveLocalWasInfo = localWasInfoRepository.save(localWasInfo);
            userDataBaseJDBC.registerErrorLogTable(new UserDatabaseErrorLog.UserConnectDto(saveLocalWasInfo.getId(), userDatabaseUrl));
            sshConnection = new SSHConnection(utilConfigure);
            // 쉘 스크립트 동작 할 수 있게
            Boolean connectWaValidation = sshConnection.connectWas(localTestDto, studentInfo.getDomainName(), studentInfo.getDatabaseName());
            if (connectWaValidation) {
                result = new WasInfoRes.LocalWasTestResultRes(saveLocalWasInfo);
            }else{
                final IntegratedErrorLog integratedErrorLog = integratedErrorLogRepository.findJoinWasId(saveLocalWasInfo.getId()).orElseThrow(() -> new LocalWasException(ErrorCode.LOCAL_NOT_SELECT_DEPLOY_METHOD));
                throw new LocalWasException(ErrorCode.LOCAL_DEPLOY_TEST_ERROR, integratedErrorLog.getErrorLogs());
            }
        }
        if (localTestDto.getName().equals(WASItem.SPRING)) {
            return null;
        }
        return result;
    }

    public WasInfoRes.LocalWasTestResultRes connectTestWasNotUpload(WasInfoReq.LocalTestDto localTestDto, StudentDtoRes.StudentFullDto studentInfo) {
        return null;
    }

    public WasInfoRes.LocalWasTestResultRes connectTestWasNotDatabase(WasInfoReq.LocalTestDto localTestDto, StudentDtoRes.StudentFullDto studentInfo) {
        return null;
    }

    public WasInfoRes.LocalWasTestResultRes connectTestWasFullOption(WasInfoReq.LocalTestDto localTestDto, StudentDtoRes.StudentFullDto studentInfo) {
        return null;
    }
}
