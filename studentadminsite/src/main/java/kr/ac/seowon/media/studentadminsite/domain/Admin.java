package kr.ac.seowon.media.studentadminsite.domain;


import kr.ac.seowon.media.studentadminsite.dto.AdminReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin")
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "passowrd", nullable = false)
    private String password;
    @Column(name = "admin_hash_code", nullable = false)
    private String hashCode;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @OneToMany(mappedBy = "admin")
    private List<Student> students = new ArrayList<>();

    protected Admin(String name, String password, String hashCode, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.hashCode = hashCode;
        this.phoneNumber = phoneNumber;
    }

    public static Admin createAdmin(String name, String password, String hashCode, String phoneNumber) {
        return new Admin(name,password,hashCode,phoneNumber);
    }

    public void modifyEntity(AdminReq.AdminDto adminDto) {
        if (adminDto.getName() != null) {
            this.name = adminDto.getName();
        }
        if (adminDto.getHashCode() != null) {
            this.hashCode = adminDto.getHashCode();
        }
        if (adminDto.getPassword() != null) {
            this.password = adminDto.getPassword();
        }
        if (adminDto.getPhoneNumber() != null) {
            this.phoneNumber = adminDto.getPhoneNumber();
        }
    }
}
