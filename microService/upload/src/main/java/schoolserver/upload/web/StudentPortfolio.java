package schoolserver.upload.web;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "student_portfolio")
@Getter
@NoArgsConstructor
public class StudentPortfolio {
    @Id
    @GeneratedValue
    @Column(name = "student_portfolio_id")
    private Integer id;
    private String name;
    @Column(unique = true)
    private Integer studentCode;
    @Column(columnDefinition = "TEXT")
    private String speechLink;
    @Column(columnDefinition = "TEXT")
    private String youtubeLink;
    private String email;
    //010-123-123 저장
    private String phoneNumber;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String subDescription;
    private Boolean isDeleted;
    //졸전위들일 경우??
    private Integer customMember;
    private String imagesFormat;
    private String password;
    // 변경
    private String job;
    private String mainDescription;


    // 회원 정보 저장
    public void registerStudent(String encodePassword, String email, String phoneNumber) {
        this.password = encodePassword;
        this.email = email;
        this.isDeleted = false;
        this.phoneNumber = phoneNumber;
    }

    public void updatePortfolio(String youtubeLink, String speechLink, String description, String insertImageFormatData, String job, String mainDescription) {
        this.youtubeLink = youtubeLink;
        this.speechLink = speechLink;
        this.description = description;
        this.imagesFormat = insertImageFormatData;
        // 변경
        this.mainDescription = mainDescription;
        this.job = job;
    }
}
