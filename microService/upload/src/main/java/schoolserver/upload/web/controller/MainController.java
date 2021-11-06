package schoolserver.upload.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import schoolserver.upload.web.MemberQueryService;
import schoolserver.upload.web.StudentPortfolioLoginForm;
import schoolserver.upload.web.UserLoginSuccessDto;
import schoolserver.upload.web.dto.DefaultStudentInfo;
import schoolserver.upload.web.dto.PortFolioForm;
import schoolserver.upload.web.exception.ErrorCode;

import java.io.File;
import java.io.IOException;

@Controller
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MemberQueryService memberQueryService;

    @Value("${file.dir}")
    private String basicFileDir;

    @GetMapping("/register")
    public String getRegisterDefaultPage() {
        return "default";
    }

    //자료 업로드
    @PostMapping("/register")
    @ResponseBody
    public Object createSiteInfo(
            @AuthenticationPrincipal UserLoginSuccessDto userLoginSuccessDto,
            @ModelAttribute PortFolioForm portFolioForm) {

        final DefaultStudentInfo studentInfo = memberQueryService.getStudentInfo(userLoginSuccessDto.getUsername());
        log.info("{}", portFolioForm.toString());
        saveStudentImages(portFolioForm, studentInfo.getStudentCode());
        return null;
    }

    private void saveStudentImages(PortFolioForm studentPortFolioDto, Integer prevStudentCode) {
        if (studentPortFolioDto.getBackgroundImage() != null) {
            if (prevStudentCode != null) {
                final File fileJPG = new File(basicFileDir + prevStudentCode.toString() + "_brochure.png");
                final File filePNG = new File(basicFileDir + prevStudentCode.toString() + "_brochure.jpg");
                if (fileJPG.exists()) {
                    fileJPG.delete();
                }
                if (filePNG.exists()) {
                    filePNG.delete();
                }
            }
            if (!studentPortFolioDto.getBackgroundImage().isEmpty()) {
                final String brochureOriginalFilename = studentPortFolioDto.getBackgroundImage().getOriginalFilename();
                final int brochureFormatIndex = brochureOriginalFilename.lastIndexOf('.');
                final String brochureFileNameFormat = brochureOriginalFilename.substring(brochureFormatIndex);
                try {
                    log.info("정보 저장");
                    studentPortFolioDto.getBackgroundImage().transferTo(new File(basicFileDir + "Hello" + "_brochure" + brochureFileNameFormat));
                    log.info("정보 저장 성공");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (studentPortFolioDto.getProfile() != null) {
            if (!studentPortFolioDto.getProfile().isEmpty()) {
                if (prevStudentCode != null) {
                    log.info("정보 확인");
                    final File fileJPG = new File(basicFileDir + prevStudentCode.toString() + "_profile.png");
                    final File filePNG = new File(basicFileDir + prevStudentCode.toString() + "_profile.jpg");
                    log.info("정보 삭제");
                    if (fileJPG.exists()) {
                        fileJPG.delete();
                    }
                    if (filePNG.exists()) {
                        filePNG.delete();
                    }
                    log.info("정보 삭제 완료");
                }
                final String profileOriginalFilename = studentPortFolioDto.getProfile().getOriginalFilename();
                final int profileFormatIndex = profileOriginalFilename.lastIndexOf('.');
                final String profileFileNameFormat = profileOriginalFilename.substring(profileFormatIndex);
                try {
                    log.info("정보 저장");
                    studentPortFolioDto.getProfile().transferTo(new File(basicFileDir + "Hello" + "_profile" + profileFileNameFormat));
                    log.info("정보 저장 성공");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

