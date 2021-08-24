package kr.ac.seowon.media.studentadminsite.dto;

import kr.ac.seowon.media.studentadminsite.exception.StudentException;
import lombok.*;

import javax.validation.constraints.*;

public class StudentReq {

    public interface FindStudentCode{}
    public interface CreateStudent {}

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class SiteInfoDto {
        @NotBlank(message = "databaseName 입력하지 않았습니다.")
        private String databaseName;
        @NotBlank(message = "domainName 입력하지 않았습니다.")
        private String domainName;
        @NotBlank(message = "기존 도메인 명은 여백이 존재하지 않습니다.")
        private String originDomain;

        public SiteInfoDto(ModifyStudentDto modifyStudentDto) {
            if (modifyStudentDto.getDomainName() != null) {
                if (modifyStudentDto.getOriginDomain() != null) {
                    domainName = modifyStudentDto.getDomainName();
                    originDomain = modifyStudentDto.getOriginDomain();
                }else{
                    throw new StudentException("기존 도메인을 입력하지 않았습니다.");
                }
            }
            if (modifyStudentDto.getDatabaseName() != null) {
                databaseName = modifyStudentDto.getDatabaseName();
            }
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class StudentDto {
        @NotBlank(message ="name이 입력하지 않았습니다.",groups = {CreateStudent.class,FindStudentCode.class})
        private String name;
        @Min(value = 200000000, message = "해당 학번 입력이 잘못되었습니다.",groups = {CreateStudent.class,FindStudentCode.class})
        @Max(value = 300000000,message ="해당 학번 입력이 잘못되었습니다." ,groups = {CreateStudent.class,FindStudentCode.class})
        private Integer studentCode;
        @NotBlank(message ="password를 입력하지 않았습니다." ,groups = {CreateStudent.class})
        private String password;
        @NotBlank(message ="email를 입력하지 않았습니다." ,groups = {CreateStudent.class})
        @Email(message = "이메일 형식을 지켜주세요.",groups = {CreateStudent.class})
        private String email;
        @NotBlank(message ="hashCode를 입력하지 않았습니다." ,groups = {CreateStudent.class})
        private String hashCode;
        @NotBlank(message ="phoneNumber를 입력하지 않았습니다." )
        private String phoneNumber;

        public StudentDto(ModifyStudentDto modifyStudentDto) {
            if (modifyStudentDto.getEmail() != null) {
                email = modifyStudentDto.getEmail();
            }
            if (modifyStudentDto.getName() != null) {
                name = modifyStudentDto.getName();
            }
            if (modifyStudentDto.getPassword() != null) {
                password = modifyStudentDto.getPassword();
            }
            if (modifyStudentDto.getPhoneNumber() != null) {
                phoneNumber = modifyStudentDto.getPhoneNumber();
            }
        }
    }

    @Getter
    @Setter
    public static class ModifyStudentDto{
        private String name;
        private String password;
        private String email;
        private String phoneNumber;
        private String databaseName;
        private String domainName;
        private String originDomain;
    }
}
