package kr.ac.seowon.media.studentadminsite.dto.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.DeployMethod;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.WASItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
        private DeployMethod deployMethod;
        private Integer port;
        private WASItem name;
        private Boolean isUploading;
        private Boolean hasDatabase;
        private String githubLink;
        private String dirLocation;
        private String deployInfo;
        private String applicationName;
        @NotNull(message = "학생의 학번을 입력해주세요")
        private Integer studentCode;
        @NotNull(message = "학생의 이름을 입력해주세요")
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
