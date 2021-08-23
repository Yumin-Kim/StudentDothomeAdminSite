package kr.ac.seowon.media.studentadminsite.domain;

import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;
    @Column(nullable = false,name = "name")
    private String name;
    @Column(nullable = false,unique = true,name = "student_code")
    private Integer studentCode;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "in_school")
    private Boolean inSchool;
    @Column(name = "in_deleted")
    private Boolean isDeleted;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_info_id")
    private SiteInfo siteInfo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Student(String name, Integer studentCode,Boolean inSchool) {
        this.name = name;
        this.studentCode = studentCode;
        this.isDeleted = false;
        if (inSchool != null){
            this.inSchool = inSchool;
        }else{
            this.inSchool = false;
        }
    }

    public static Student createStudent(AdminObserveReq.BasicStudentDto basicStudentDto){
        return new Student(basicStudentDto.getName(), basicStudentDto.getStudentCode(), null);)
    }

    public void modifyStudent(StudentReq.StudentDto studentDto, Admin admin, SiteInfo siteInfo) {
        if (studentDto.getPassword() != null) {
            password = studentDto.getPassword();
        }
        if (studentDto.getPhoneNumber() != null) {
            phoneNumber = studentDto.getPhoneNumber();
        }
        if (studentDto.getEmail() != null) {
            email = studentDto.getEmail();
        }
        this.admin = admin;
        this.siteInfo = siteInfo;
    }
}
