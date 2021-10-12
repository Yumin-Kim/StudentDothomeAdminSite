package kr.ac.seowon.media.studentadminsite.dto;

import kr.ac.seowon.media.studentadminsite.domain.StudentPortfolio;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class StudentPortfolioReq {

    //기본 정보
    @Getter
    @Setter
    public static class StudentPortFolioBasicInfoDto{
        private String name;
        private Integer studentCode;
    }

    @Getter
    @Setter
    @ToString
    public static class StudentPortFolieSignUpDto extends StudentPortFolioBasicInfoDto{
        private String password;
    }

    @Getter
    @Setter
    public static class StudentPortFolioDto {
        private String name;
        private Integer studentCode;
        private MultipartFile profileFile;
        private MultipartFile brochureFile;
        private String youtubeLink;
        private String description;

        public StudentPortfolio toEntity() {
            return StudentPortfolio.createEntity(this.name, this.studentCode, this.youtubeLink, this.description,findImageFormat(profileFile),findImageFormat(brochureFile));
        }
        private String findImageFormat(MultipartFile fileInfo){
            final String originalFilename = fileInfo.getOriginalFilename();
            final int formatIndex = originalFilename.lastIndexOf('.');
            return originalFilename.substring(formatIndex);
        }
    }

    @Getter
    @Setter
    public static  class ModifiedDto{
        private MultipartFile profileFile;
        private MultipartFile brochureFile;
        private String youtubeLink;
        private String description;
    }

}
