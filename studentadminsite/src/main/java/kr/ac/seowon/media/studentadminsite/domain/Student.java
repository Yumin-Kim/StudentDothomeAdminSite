package kr.ac.seowon.media.studentadminsite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private Integer studentCode;
    private String password;
    private String email;

    protected Student(String name, Integer studentCode) {
        this.name = name;
        this.studentCode = studentCode;
    }

    public static Student createStudent(String name, Integer studentCode) {
        return new Student(name, studentCode);
    }


}
