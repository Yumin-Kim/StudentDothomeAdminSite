package kr.ac.seowon.media.studentadminsite.dto;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

public class StudentReq {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ModifySiteInfo {
        @NotBlank(message = "databaseName 입력하지 않았습니다.")
        private String databaseName;
        @NotBlank(message = "domainName 입력하지 않았습니다.")
        private String domainName;
        @NotBlank(message = "기존 도메인 명은 여백이 존재하지 않습니다.")
        private String originDomain;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class StudentDto {
        @NotBlank(message ="name이 입력하지 않았습니다." )
        private String name;
        @Min(value = 200000000, message = "해당 학번 입력이 잘못되었습니다.")
        @Max(value = 300000000,message ="해당 학번 입력이 잘못되었습니다." )
        @NotBlank(message = "studentCode를 입력하지 않았습니다.")
        private Integer studentCode;
        @NotBlank(message ="password를 입력하지 않았습니다." )
        private String password;
        @NotBlank(message ="email를 입력하지 않았습니다." )
        @Email(message = "이메일 형식을 지켜주세요.")
        private String email;
        @NotBlank(message ="hashCode를 입력하지 않았습니다." )
        private String hashCode;
        @NotBlank(message ="phoneNumber를 입력하지 않았습니다." )
        private String phoneNumber;

    }
}
