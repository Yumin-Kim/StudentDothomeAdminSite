package kr.ac.seowon.media.studentadminsite.dto.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.DeployMethod;
import kr.ac.seowon.media.studentadminsite.domain.wasDomain.LocalWasInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WasInfoRes {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocalWasTestResultRes{
        private DeployMethod deployMethod;
        private String port;
        private String name;
        private String isUploading;
        private String hasDatabase;
        private String githubLink;
        private String dirLocation;

        public LocalWasTestResultRes(LocalWasInfo saveLocalWasInfo) {

        }
    }

}
