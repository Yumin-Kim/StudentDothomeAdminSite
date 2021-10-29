package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import javax.persistence.Enumerated;


public enum DeployMethod {
    HTTP("HTTP 프로토콜 업로드"),
    GIT("GIT 원격 업로드"),
    SFTP("SFTP 파일 업로드");
    private String description;

    DeployMethod(String description) {
        this.description = description;
    }
}
