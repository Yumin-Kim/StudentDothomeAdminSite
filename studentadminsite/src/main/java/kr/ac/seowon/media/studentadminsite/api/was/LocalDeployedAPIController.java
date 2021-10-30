package kr.ac.seowon.media.studentadminsite.api.was;

import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoReq;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoRes;
import kr.ac.seowon.media.studentadminsite.service.student.StudentQueryService;
import kr.ac.seowon.media.studentadminsite.service.was.LocalWasQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로컬에서 업로드할 수 있는 API 담당 Controller
 */
@RestController
@RequiredArgsConstructor
public class LocalDeployedAPIController {

    private final LocalWasQueryService localWasQueryService;
    private final StudentQueryService studentQueryService;
    /**
     * was 배포간 테스트 과정
     * @param localTestDto
     * @return
     */
    public Res initializeWASInfo(@RequestBody WasInfoReq.LocalTestDto localTestDto) {
        final StudentDtoRes.StudentFullDto studentInfo = studentQueryService.findStudentInfo(localTestDto.getStudentName(), localTestDto.getStudentCode());
        WasInfoRes.LocalWasTestResultRes res = null;
        if (!localTestDto.getHasDatabase() && !localTestDto.getIsUploading())
            res = localWasQueryService.connectTestWasNotOption(localTestDto,studentInfo);
        else if(localTestDto.getHasDatabase() && !localTestDto.getIsUploading())
            res = localWasQueryService.connectTestWasNotUpload(localTestDto,studentInfo);
        else if(!localTestDto.getHasDatabase() && localTestDto.getIsUploading())
            res = localWasQueryService.connectTestWasNotDatabase(localTestDto,studentInfo);
        else if(localTestDto.getHasDatabase() && localTestDto.getIsUploading())
            res = localWasQueryService.connectTestWasFullOption(localTestDto,studentInfo);
        return Res.isOkWithData(res,"테스트 성공하였습니다.");
    }
    // 등록
    // 생성
    // 삭제
    // 사용자 로그 확인
    // 본인 정보 확인(로그)


}
