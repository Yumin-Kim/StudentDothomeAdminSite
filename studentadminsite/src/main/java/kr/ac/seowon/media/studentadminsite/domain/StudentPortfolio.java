package kr.ac.seowon.media.studentadminsite.domain;

import kr.ac.seowon.media.studentadminsite.api.StudentPortfolioController;
import kr.ac.seowon.media.studentadminsite.dto.StudentPortfolioReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

//이름 학번 글귀 유튜브 링크 팀장
@Entity
@Getter
@Table(name = "student_portfolio")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentPortfolio extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "student_portfolio_id")
    private Integer id;
    private String name;
    @Column(unique = true)
    private Integer studentCode;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String youtubeLink;
    private String profileImageFormat;
    private String brochureImageFormat;
    private String password;

    protected StudentPortfolio(String name, Integer studentCode, String description, String youtubeLink, String profileImageFormat, String brochureImageFormat) {
        this.name = name;
        this.studentCode = studentCode;
        this.description = description;
        this.youtubeLink = youtubeLink;
        this.profileImageFormat = profileImageFormat;
        this.brochureImageFormat = brochureImageFormat;
    }

    public static StudentPortfolio createEntity(String name, Integer studentCode, String youtubeLink, String description, String profileImageFormat, String brochureImageFormat) {
        return new StudentPortfolio(name, studentCode, description, youtubeLink, profileImageFormat, brochureImageFormat);
    }

    public void updateEntity(StudentPortfolioReq.StudentPortFolioDto studentPortFolioDto) {
        if (studentPortFolioDto.getStudentCode() != null) {
            this.studentCode = studentPortFolioDto.getStudentCode();
        }
        if (StringUtils.hasText(studentPortFolioDto.getDescription())) {
            this.description = studentPortFolioDto.getDescription();
        }
        if (StringUtils.hasText(studentPortFolioDto.getName())) {
            this.name = studentPortFolioDto.getName();
        }
        if (StringUtils.hasText(studentPortFolioDto.getYoutubeLink())) {
            this.youtubeLink = studentPortFolioDto.getYoutubeLink();
        }
        if (studentPortFolioDto.getBrochureFile() != null) {
            if (!studentPortFolioDto.getBrochureFile().isEmpty()) {
                final String originalFilename = studentPortFolioDto.getBrochureFile().getOriginalFilename();
                final int formatIndex = originalFilename.lastIndexOf('.');
                brochureImageFormat = originalFilename.substring(formatIndex);
            }
        }
        if (studentPortFolioDto.getProfileFile() != null) {
            if (!studentPortFolioDto.getProfileFile().isEmpty()) {
                final String originalFilename = studentPortFolioDto.getProfileFile().getOriginalFilename();
                final int formatIndex = originalFilename.lastIndexOf('.');
                profileImageFormat = originalFilename.substring(formatIndex);
            }
        }
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
