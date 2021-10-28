package kr.ac.seowon.media.studentadminsite.dto.admin;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdminReq {

    public interface createAdmin{}
    public interface modifyAdmin{}

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class AdminDto{
        @NotBlank(message = "name이 공백입니다",groups = {createAdmin.class})
        private String name;
        @NotBlank(message = "hashCode가 공백입니다",groups = {createAdmin.class})
        private String hashCode;
        @NotBlank(message = "phoneNumber가 공백입니다",groups = {createAdmin.class})
        private String phoneNumber;
        @NotBlank(message = "password가 공백입니다",groups = {createAdmin.class})
        private String password;

        public Admin toEntity() {
            return Admin.createAdmin(this.name, this.password, this.hashCode, this.phoneNumber);
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminLoginDto {
        @NotNull(message = "name이 존재 하지 않습니다.")
        @NotBlank(message = "name이 공백입니다")
        private String name;
        @NotNull(message = "password가 존재 하지 않습니다.")
        @NotBlank(message = "password가 공백입니다")
        private String password;
    }
}
