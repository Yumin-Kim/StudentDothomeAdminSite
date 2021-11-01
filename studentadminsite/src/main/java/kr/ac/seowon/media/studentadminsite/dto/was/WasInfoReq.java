package kr.ac.seowon.media.studentadminsite.dto.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.DeployMethod;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.WASItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 테스트 dto => __-TestDto
 * 성공시 dto => __FormDto
 */
public class WasInfoReq {

    @Getter
    @Setter
    public static class DockerFormDto {
        private Integer port;
        private String containerName;
        private String dockerImage;
        private String uploadRemote;
        private Boolean isUploading;
        private Boolean hasDatabase;
        private String description;
        private String envOption;
        private String volumeOption;
        private String entrypointOption;
        private String dockercommand;
    }


    @Getter
    @Setter
    public static class LocalFormDto {
        private DeployMethod deployMethod;
        private Integer port;
        private String name;
        private Boolean isUploading;
        private Boolean hasDatabase;
        private String githubLink;
        private String description;
        private String dirLocation;
        private String studentName;
        private String studentCode;
    }


    @Getter
    @Setter
    public static class DockerTestDto {
        private Integer port;
        private String containerName;
        private String dockerImage;
        private String uploadRemote;
        private Boolean isUploading;
        private Boolean hasDatabase;
        private String envOption;
        private String volumeOption;
        private String entrypointOption;
        private String dockercommand;
    }


    @Getter
    @Setter
    public static class LocalTestDto {
//        @NotBlank
        private DeployMethod deployMethod;
        @Min(message = "최소 입력 가능 포트는 : 0",value = 0)
        @Max(message = "최대 입력 가능 포트는 : 65535" ,value = 65535)
        private Integer port;
//        @NotBlank
        private WASItem name;
        @NotNull
        private Boolean isUploading;
        @NotNull
        private Boolean hasDatabase;
        private String githubLink;
        //업로드 한 위치 (wasWorkspace 기본 디렉토리 이며 해당 디렉토이 안에서 생성해야 동작할 수 있게끔 )
        // /wasWorkspace/____ >>_____디렉토리 이름 적어야
        // GIT 사용시 리파지토리 이름을 입
        @NotBlank
        private String dirLocation;
        private String deployInfo;
        @NotBlank
        private String applicationName;
        @NotBlank
        private String endPointJS;
        @NotNull(message = "학생의 학번을 입력해주세요")
        private Integer studentCode;
        @NotBlank(message = "학생의 이름을 입력해주세요")
        private String studentName;
        private MultipartFile uploadFile;
    }


    @Getter
    @Setter
    public static class LocalDeleteDto {
        private Integer studentId;
        private Integer wasInfoId;
    }


}
