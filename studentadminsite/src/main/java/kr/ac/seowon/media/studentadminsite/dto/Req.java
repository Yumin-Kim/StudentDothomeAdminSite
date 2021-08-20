package kr.ac.seowon.media.studentadminsite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Req {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SiteInfo {
        @NotNull(message = "databaseName 입력하지 않았습니다.")
        private String databaseName;
        @NotNull(message = "domainName 입력하지 않았습니다.")
        private String domainName;
        @NotNull(message = "originDomain 입력하지 않았습니다.")
        @NotBlank(message = "기존 도메인 명은 여백이 존재하지 않습니다.")
        private String originDomain;
    }
}
