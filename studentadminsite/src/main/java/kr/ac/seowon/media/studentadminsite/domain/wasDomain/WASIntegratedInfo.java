package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import kr.ac.seowon.media.studentadminsite.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WASIntegratedInfo extends BaseEntity {

    @Column(unique = true,nullable = false)
    private Integer port;

    @Column(unique = true , nullable = false)
    private String applicationName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WASItem name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Boolean isRunning;
    private Boolean isUploading;
    private Boolean isDeleted;
    private Boolean hasDataBase;
    private String uploadLocation;

    public WASIntegratedInfo(Integer port, WASItem name,String applicationName) {
        this.port = port;
        this.name = name;
        this.applicationName = applicationName;
        this.isRunning = true;
        this.isDeleted = false;
    }


}
