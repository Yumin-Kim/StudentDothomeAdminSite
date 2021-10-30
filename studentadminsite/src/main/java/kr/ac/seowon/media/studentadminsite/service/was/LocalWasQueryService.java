package kr.ac.seowon.media.studentadminsite.service.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.DeployMethod;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.LocalWasInfo;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.WASItem;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoReq;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoRes;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.LocalWasException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.was.LocalWasInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LocalWasQueryService {

    private final LocalWasInfoRepository localWasInfoRepository;

    //중복 검색
    private void validationLocalWasInfo(Integer port){
        localWasInfoRepository.findByPort(port)
                .map(localWasInfo -> {
                    if (localWasInfo.getHasDataBase() == null)
                        throw new LocalWasException(ErrorCode.LOCAL_DUPLICATE_PORT);
                    return null;
                })
                .orElseGet(null);
    }

    /**
     * 테스트 진행전 아무런 옵션없이 was 동작 테스트 진행
     * @param localTestDto
     * @param studentInfo
     * @return
     */
    public WasInfoRes.LocalWasTestResultRes connectTestWasNotOption(WasInfoReq.LocalTestDto localTestDto, StudentDtoRes.StudentFullDto studentInfo) {
        validationLocalWasInfo(localTestDto.getPort());
        if (localTestDto.getName().equals(WASItem.NODEJS)) {
            switch (localTestDto.getDeployMethod()){
                case HTTP:
                    break;
                case GIT:
                    /*
                     쉘 사용자 정보를 활용해 등록
                     사용자 디렉토리에 작업 디렉토리(nodejsWorkspace) 존재 여부에 따라 생성
                     git clone ___
                     cd $매개변수(applicationName)
                     npm i
                     pm2 start app.js --name applicationName
                     pm2 show applicationName
                     결과 성공 여부를 어떻게 제공할지??
                     */
                    break;
                case SFTP:
                    break;
                default:
                    throw new StudentException(ErrorCode.LOCAL_NOT_SELECT_DEPLOY_METHOD);
            }
            // 학생이 업로드한 갯수 확인
            // 사용자의 디렉토리 생성 여부 파악 후 업로드한 Nodejs 업로드 진행
            // 쉘 스크립트를 통해 npm i >> npm run start(또는 pm2 start app.js -학생 도메인 정보 + was보여갯수 )
            // 테스트 진행후 결과 전송 >> 성공 및 에러에 따라 전송

        }
        if (localTestDto.getName().equals(WASItem.SPRING)) {

        }
        return null;
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
