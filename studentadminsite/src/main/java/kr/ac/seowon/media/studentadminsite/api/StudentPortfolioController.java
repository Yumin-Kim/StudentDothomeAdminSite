//    생성 수정 확인 기능만 제공
    /*
    학생 포폴 정보 저장 api
    학번으로 php 에서 정보 제공
    media.seowon.ac.kr/detail.php?studentCode=201610309
    개인 이미지 : media.seowon.ac.kr/studentInfo/studentCode/person.png
    브로셔 이미지 : media.seowon.ac.kr/studentInfo/studentCode/project.png
    영상 : media.seowon.ac.kr/studentInfo/studentCode/video.mp3
    또는 유튜브 링크
    글귀는 select * from utilInfo where studentCode=201610309
    디렉토리 생성해서 이미지나 글귀 저장하는 씩으로 진행
    DB : 이름 학번 글귀
    개인 : 브로셔 이미지 , 개인 사진  , 영상 , 개인 이름 , 글귀
    (기획 디자인)팀
    (u-300) 브로셔??
     */
//조회후 데이터 베이스 확인하고 존재 여부만 알려줌

package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.domain.StudentPortfolio;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.dto.StudentPortFolioRes;
import kr.ac.seowon.media.studentadminsite.dto.StudentPortfolioReq;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.exception.utilexception.FileUploadException;
import kr.ac.seowon.media.studentadminsite.repository.StudentPortfolioRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
@Slf4j
public class StudentPortfolioController {

    private final StudentPortfolioRepository studentPortfolioRepository;

    @Value("${file.dir}")
    private String basicFileDir;

    /**
     * 학번 조회용 api
     * 조회 결과 데이터가 있으면 메세지 전송 프론트는 메세지를 통해 수정할 수 있는 페이지 보여주게끔
     *
     * @param studentCode 학생  학번
     * @return
     */
    @GetMapping("/person/{studentCode}")
    @Deprecated
    @Transactional(readOnly = true)
    public Res findPersonPortfolio(@PathVariable("studentCode") Integer studentCode) {
        String comment = (String) studentPortfolioRepository.findByStudentCode(studentCode)
                .map(studentPortfolio -> {
                    if (studentPortfolio.getStudentCode() != null) {
                        throw new StudentException(ErrorCode.STUDENT_HAS_DATA.getMessage());
                    }
                    return null;
                }).orElseGet(() -> "학생 정보를 생성해주세요");
        return Res.isOkByMessage(comment);
    }

    /**
     * 학생 계정 생성
     *
     * @param signupDto 이름 학번 비밀번호 security 사용하지 않음
     * @return basic하게 이름 학번만 전송
     */
    @PostMapping("/person/signup")
    @Transactional
    public Res createStudentBasicInfo(@RequestBody StudentPortfolioReq.StudentPortFolieSignUpDto signupDto) {
        final StudentPortfolio studentPortfolio = studentPortfolioRepository.findByStudentCodeAndName(signupDto.getStudentCode(), signupDto.getName())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        if (hasText(studentPortfolio.getPassword())) {
            return Res.isOkWithData(new StudentPortFolioRes.BasicInfoDto(studentPortfolio.getName(), studentPortfolio.getStudentCode()), "현재 비밀번호는 " + studentPortfolio.getPassword() + "입니다");
        }
        studentPortfolio.updatePassword(signupDto.getPassword());
        return Res.isOkWithData(new StudentPortFolioRes.BasicInfoDto(studentPortfolio.getName(), studentPortfolio.getStudentCode()), "계정이 생성 되었습니다.");
    }

    /**
     * 학생 로그인
     * 학번 비밀번호만으로 로그인 여부 파악 security 사용하지 않음
     *
     * @param loginDto 학번 비밀번호
     * @return
     */
    @PostMapping("/person/login")
    @Transactional(readOnly = true)
    public Res findStudentPortFolieInfoAndLogin(@RequestBody StudentPortfolioReq.StudentPortFolieSignUpDto loginDto) {
        final StudentPortfolio studentPortfolio = studentPortfolioRepository.findByStudentCodeAndPassword(loginDto.getStudentCode(), loginDto.getPassword())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_ID_NOT_FOUND));
        return Res.isOkWithData(new StudentPortFolioRes.BasicInfoDto(studentPortfolio.getName(), studentPortfolio.getStudentCode()), "로그인 성공!");
    }


    /**
     * 학생 정보 저장
     * 학생 중복 체크 후 이미지 저장 >> db 저장하는 방식으로 진행
     *
     * @param studentPortFolioDto 학생 , 이름 , 링크 , 소개글 , 프로필이미지 , 브로시어이미지
     * @return
     */
    @PostMapping("/person")
    @Transactional
    public Res createPersonPortfolio(@ModelAttribute StudentPortfolioReq.StudentPortFolioDto studentPortFolioDto) {
        StudentPortfolio studentPortfolio = (StudentPortfolio) studentPortfolioRepository.findByStudentCodeAndName(studentPortFolioDto.getStudentCode(), studentPortFolioDto.getName())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        saveStudentImages(studentPortFolioDto, null);
        studentPortfolio.updateEntity(studentPortFolioDto);
        studentPortfolioRepository.save(studentPortfolio);
        return Res.isOkWithData(new StudentPortFolioRes.StandardInfoDto(studentPortfolio), "등록이 완료 되었습니다.");
    }

    @PutMapping("/person/{studentCode}")
    @Transactional
    public Res modifyPersonPortfolio(
            @PathVariable Integer studentCode,
            @ModelAttribute StudentPortfolioReq.StudentPortFolioDto studentPortFolioDto) {
        final StudentPortfolio studentPortfolio = studentPortfolioRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        studentPortfolio.updateEntity(studentPortFolioDto);
        saveStudentImages(studentPortFolioDto, studentCode);
        return Res.isOkWithData(new StudentPortFolioRes.StandardInfoDto(studentPortfolio), "정보 수정 완료 하였습니다.");
    }


    @ExceptionHandler(FileUploadException.class)
    public Res fileException(Exception e) {
        return Res.isOkByMessage(e.getMessage());
    }

    private void saveStudentImages(StudentPortfolioReq.StudentPortFolioDto studentPortFolioDto, Integer prevStudentCode) {
        if (studentPortFolioDto.getBrochureFile() != null) {
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
            if (!studentPortFolioDto.getBrochureFile().isEmpty()) {
                final String brochureOriginalFilename = studentPortFolioDto.getBrochureFile().getOriginalFilename();
                final int brochureFormatIndex = brochureOriginalFilename.lastIndexOf('.');
                final String brochureFileNameFormat = brochureOriginalFilename.substring(brochureFormatIndex);
                try {
                    studentPortFolioDto.getBrochureFile().transferTo(new File(basicFileDir + studentPortFolioDto.getStudentCode().toString() + "_brochure" + brochureFileNameFormat));
                } catch (IOException e) {
                    try {
                        throw new FileUploadException(ErrorCode.IMAGE_UPLOAD_ERROR);
                    } catch (FileUploadException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        if (studentPortFolioDto.getProfileFile() != null) {
            if (!studentPortFolioDto.getProfileFile().isEmpty()) {
                if (prevStudentCode != null) {
                    final File fileJPG = new File(basicFileDir + prevStudentCode.toString() + "_profile.png");
                    final File filePNG = new File(basicFileDir + prevStudentCode.toString() + "_profile.jpg");
                    if (fileJPG.exists()) {
                        fileJPG.delete();
                    }
                    if (filePNG.exists()) {
                        filePNG.delete();
                    }
                }
                final String profileOriginalFilename = studentPortFolioDto.getProfileFile().getOriginalFilename();
                final int profileFormatIndex = profileOriginalFilename.lastIndexOf('.');
                final String profileFileNameFormat = profileOriginalFilename.substring(profileFormatIndex);
                try {
                    studentPortFolioDto.getProfileFile().transferTo(new File(basicFileDir + studentPortFolioDto.getStudentCode().toString() + "_profile" + profileFileNameFormat));
                } catch (IOException e) {
                    try {
                        throw new FileUploadException(ErrorCode.IMAGE_UPLOAD_ERROR);
                    } catch (FileUploadException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


}
