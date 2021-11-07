package schoolserver.upload.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import schoolserver.upload.web.StudentPortfolio;

import javax.persistence.Column;

@Getter
@Setter
@ToString
public class DefaultStudentInfo {

    private String name;
    private Integer studentCode;
    private String speechLink;
    private String youtubeLink;
    private String email;
    private String phoneNumber;
    private String description;
    private String subDescription;
    private Boolean isDeleted;
    private Boolean customMember;
    private String imagesFormat;
    private String password;

    public DefaultStudentInfo(StudentPortfolio findStudent) {
        if (findStudent.getSpeechLink() != null) {
            this.speechLink = findStudent.getSpeechLink();
            this.youtubeLink = findStudent.getYoutubeLink();
            this.imagesFormat = findStudent.getImagesFormat();
        }
        this.name = findStudent.getName();
        this.studentCode = findStudent.getStudentCode();
        this.email = findStudent.getName();
        this.phoneNumber = findStudent.getPhoneNumber();
    }

}
