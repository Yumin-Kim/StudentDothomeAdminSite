package kr.ac.seowon.media.studentadminsite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_info_id")
    private SiteInfo siteInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    protected Student(String name, Integer studentCode) {
        this.name = name;
        this.studentCode = studentCode;
    }

    public static Student createStudent(String name, Integer studentCode) {
        return new Student(name, studentCode);
    }


}
