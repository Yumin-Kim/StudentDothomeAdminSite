package schoolserver.upload.web;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name ="student_portfolio" )
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
    private Boolean customMember;
    private String imagesFormat;
    private String password;

    // 회원 정보 저장
    public void registerStudent(String encodePassword, String email , String phoneNumber) {
        this.password = encodePassword;
        this.email = email;
        this.isDeleted = false;
        this.phoneNumber = phoneNumber;
    }
}
