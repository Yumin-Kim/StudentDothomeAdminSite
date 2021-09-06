package kr.ac.seowon.media.studentadminsite.domain;

import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
public class Student extends BaseEntity {
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

    public Student(String name, Integer studentCode,Boolean inSchool,Admin admin) {
        this.name = name;
        this.studentCode = studentCode;
        this.isDeleted = false;
        if (inSchool != null){
            this.inSchool = inSchool;
        }else{
            this.inSchool = true;
        }
        this.admin = admin;
    }

    public static Student createStudent(AdminObserveReq.BasicStudentDto basicStudentDto, Admin admin){
        return new Student(basicStudentDto.getName(), basicStudentDto.getStudentCode(), null,admin);
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
        if (admin != null) {
            this.admin = admin;
        }
        if (siteInfo != null) {
            this.siteInfo = siteInfo;
        }
    }

    public void adminPermitModifyStudent(AdminObserveReq.AdminModifyStudentDto modifyStudentDto) {
        if (modifyStudentDto.getInSchool() != null) {
            inSchool = modifyStudentDto.getInSchool();
        }
        if (modifyStudentDto.getIsDeleted() != null) {
            isDeleted = modifyStudentDto.getIsDeleted();
        }
        if (modifyStudentDto.getPhoneNumber() !=null) {
            phoneNumber = modifyStudentDto.getPhoneNumber();
        }
        if (modifyStudentDto.getName() != null) {
            name = modifyStudentDto.getName();
        }
        if (modifyStudentDto.getEmail() != null) {
            email = modifyStudentDto.getEmail();
        }
        if (modifyStudentDto.getPassword() != null) {
            password = modifyStudentDto.getPassword();
        }
    }

    public void disabledStudent(Boolean change) {
        isDeleted = change;
        email = null;
        phoneNumber = null;
        admin = null;
        siteInfo = null;
    }
}
