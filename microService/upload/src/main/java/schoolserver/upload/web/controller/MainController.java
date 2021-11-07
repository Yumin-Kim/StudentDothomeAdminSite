package schoolserver.upload.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import schoolserver.upload.web.MemberQueryService;
import schoolserver.upload.web.MemberService;
import schoolserver.upload.web.UserLoginSuccessDto;
import schoolserver.upload.web.dto.DefaultStudentInfo;
import schoolserver.upload.web.dto.PortFolioForm;
import schoolserver.upload.web.exception.ErrorCode;
import schoolserver.upload.web.exception.StudentException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MemberQueryService memberQueryService;
    private final MemberService memberService;

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
        DefaultStudentInfo studentInfo = getDefaultStudentInfo(userLoginSuccessDto);
        log.info("{}", portFolioForm.toString());
        String insertImageFormatData = saveStudentImages(portFolioForm, studentInfo.getStudentCode()).toString();
        final String replaceInsertImageFormatData = insertImageFormatData.replace("[", "");
        final String replaceResult = replaceInsertImageFormatData.replace("]", "");
        memberService.registerPortfolio(replaceResult, portFolioForm, studentInfo);
        return "업로드 성공하였습니다.";
    }

    @GetMapping("/modified")
    public String modifiedSiteInfo(){
        return "index";
    }

    @Transactional
    private DefaultStudentInfo getDefaultStudentInfo(UserLoginSuccessDto userLoginSuccessDto) {
        DefaultStudentInfo studentInfo = memberQueryService.getStudentInfo(userLoginSuccessDto.getUsername());
        return studentInfo;
    }

    private List<String> saveStudentImages(PortFolioForm portFolioForm, Integer prevStudentCode){
        List<String> arrayList = new ArrayList<>();
        try {
            final Field[] declaredFields = portFolioForm.getClass().getDeclaredFields();
            final List<String> propertyCollect = Arrays.stream(declaredFields)
                    .filter(a -> a.getType() != String.class)
                    .map(data -> data.getName())
                    .collect(Collectors.toList());
            for (String parseClassProperty : propertyCollect) {
                final Method declaredMethod = portFolioForm.getClass().getDeclaredMethod("get" + upperCaseFirst(parseClassProperty));
                final MultipartFile getMethod = (MultipartFile) declaredMethod.invoke(portFolioForm);
                if (getMethod != null) {
                    if (prevStudentCode != null) {
                        final File fileJPG = new File(basicFileDir + prevStudentCode.toString() + "_" + parseClassProperty + ".png");
                        final File filePNG = new File(basicFileDir + prevStudentCode.toString() + "_" + parseClassProperty + ".jpg");
                        if (fileJPG.exists()) {
                            fileJPG.delete();
                        }
                        if (filePNG.exists()) {
                            filePNG.delete();
                        }
                    }
                    final String originalFilename = getMethod.getOriginalFilename();
                    final int formatIndex = originalFilename.lastIndexOf('.');
                    final String fileNameFormat = originalFilename.substring(formatIndex);
                    try {
                        log.info("정보 저장");
                        getMethod.transferTo(new File(basicFileDir + prevStudentCode + "_" + parseClassProperty + fileNameFormat));
                        arrayList.add(parseClassProperty + fileNameFormat);
                        log.info("정보 저장 성공");
                    } catch (IOException e) {
                        throw new StudentException(ErrorCode.UPLOAD_ERROR);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    private String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

}

