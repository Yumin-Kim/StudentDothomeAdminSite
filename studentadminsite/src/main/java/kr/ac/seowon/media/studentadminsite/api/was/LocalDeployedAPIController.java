package kr.ac.seowon.media.studentadminsite.api.was;

import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoReq;
import kr.ac.seowon.media.studentadminsite.dto.was.WasInfoRes;
import kr.ac.seowon.media.studentadminsite.service.student.StudentQueryService;
import kr.ac.seowon.media.studentadminsite.service.was.LocalWasCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 로컬에서 업로드할 수 있는 API 담당 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/was")
public class LocalDeployedAPIController {

    private final LocalWasCommandService localWasCommandService;
    private final StudentQueryService studentQueryService;
    /**
     * was 배포간 테스트 과정
     * @param localTestDto
     * @return
     */
    @PostMapping
    public Res initializeWASInfo(@ModelAttribute @Valid WasInfoReq.LocalTestDto localTestDto) {
        final StudentDtoRes.StudentFullDto studentInfo = studentQueryService.findStudentInfo(localTestDto.getStudentName(), localTestDto.getStudentCode());
        WasInfoRes.LocalWasTestResultRes res = null;
        if (!localTestDto.getHasDatabase() && !localTestDto.getIsUploading())
            res = localWasCommandService.connectTestWasNotOption(localTestDto,studentInfo);
        else if(localTestDto.getHasDatabase() && !localTestDto.getIsUploading())
            res = localWasCommandService.connectTestWasNotUpload(localTestDto,studentInfo);
        else if(!localTestDto.getHasDatabase() && localTestDto.getIsUploading())
            res = localWasCommandService.connectTestWasNotDatabase(localTestDto,studentInfo);
        else if(localTestDto.getHasDatabase() && localTestDto.getIsUploading())
            res = localWasCommandService.connectTestWasFullOption(localTestDto,studentInfo);
        return Res.isOkWithData(res,"테스트 성공하였습니다.");
    }
    // 등록
    // 생성
    // 삭제
    // 사용자 로그 확인
    // 본인 정보 확인(로그)


}
