package kr.ac.seowon.media.studentadminsite.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

}
