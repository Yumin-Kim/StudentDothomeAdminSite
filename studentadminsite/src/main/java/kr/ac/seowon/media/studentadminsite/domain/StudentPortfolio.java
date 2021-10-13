package kr.ac.seowon.media.studentadminsite.domain;

import kr.ac.seowon.media.studentadminsite.api.StudentPortfolioController;
import kr.ac.seowon.media.studentadminsite.dto.StudentPortfolioReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static org.springframework.util.StringUtils.hasText;

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
    private String email;
    private String phoneNumber;
    private String slogan;
    private String job;

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
        if (hasText(studentPortFolioDto.getDescription())) {
            this.description = studentPortFolioDto.getDescription();
        }
        if (hasText(studentPortFolioDto.getName())) {
            this.name = studentPortFolioDto.getName();
        }
        if (hasText(studentPortFolioDto.getYoutubeLink())) {
            this.youtubeLink = studentPortFolioDto.getYoutubeLink();
        }
        if (hasText(studentPortFolioDto.getJob())) {
            this.job = studentPortFolioDto.getJob();
        }
        if (hasText(studentPortFolioDto.getEmail())) {
            this.email = studentPortFolioDto.getEmail();
        }
        if (hasText(studentPortFolioDto.getPhoneNumber())) {
            this.phoneNumber = studentPortFolioDto.getPhoneNumber();
        }
        if (hasText((studentPortFolioDto.getSlogan()))) {
            this.slogan = studentPortFolioDto.getSlogan();
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

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
