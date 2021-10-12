package kr.ac.seowon.media.studentadminsite.dto;

import kr.ac.seowon.media.studentadminsite.domain.StudentPortfolio;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.hasText;

public class StudentPortFolioRes {

    @Getter
    @Setter
    @ToString
    public static class BasicInfoDto{
        private String name;
        private Integer studentCode;

        public BasicInfoDto(String name, Integer studentCode) {
            this.name = name;
            this.studentCode = studentCode;
        }
    }

    @Getter
    @Setter
    public static class StandardInfoDto{
        private String name;
        private Integer studentCode;
        private LocalDateTime lastModifiedAt;
        private String profileImageSrc;
        private String brochureImageSrc;
        private String description;
        private String youtubeLink;

        public StandardInfoDto(StudentPortfolio studentPortfolio) {
            name = studentPortfolio.getName();
            studentCode = studentPortfolio.getStudentCode();
            lastModifiedAt = studentPortfolio.getLastModifiedDate();
            description = studentPortfolio.getDescription();
            youtubeLink = studentPortfolio.getYoutubeLink();
            if (hasText(studentPortfolio.getBrochureImageFormat()) ){
                brochureImageSrc = "/studentImages/" + studentPortfolio.getStudentCode().toString() + "_brochure" + studentPortfolio.getBrochureImageFormat();
            }
            if (hasText(studentPortfolio.getProfileImageFormat()))
            profileImageSrc = "/studentImages/" + studentPortfolio.getStudentCode().toString() + "_profile" + studentPortfolio.getProfileImageFormat();
        }
    }

}
