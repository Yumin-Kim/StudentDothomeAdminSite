package kr.ac.seowon.media.studentadminsite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "site_info")
public class SiteInfo {

    @Id
    @GeneratedValue
    @Column(name = "site_info_id")
    private Integer id;

    @Column(name = "domain_name")
    private String domainName;
    @Column(name = "database_name")
    private String databaseName;

    @OneToOne(mappedBy = "siteInfo",fetch = FetchType.LAZY)
    private Student student;

    protected SiteInfo(String domainName, String databaseName) {
        this.databaseName = databaseName;
        this.domainName = domainName;
    }

    public static SiteInfo createSiteInfo(String domainName, String databaseName) {
        return new SiteInfo(domainName, databaseName);
    }
}
