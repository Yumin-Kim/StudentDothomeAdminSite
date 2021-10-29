package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class WASIntegratedInfo {

    @Column(unique = true,nullable = false)
    private Integer port;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WASItem name;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;
    private Boolean isRunning;
    private Boolean isUploading;
    private Boolean isDeleted;
    private Boolean hasDataBase;
    private String uploadLocation;

}
