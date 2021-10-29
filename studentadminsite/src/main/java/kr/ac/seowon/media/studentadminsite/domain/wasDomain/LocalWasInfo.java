package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "local_was_info")
public class LocalWasInfo extends WASIntegratedInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_was_info_id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    private DeployMethod deployMethod;
    private String githubLink;
    @Column(columnDefinition = "TEXT")
    private String sftpLocation;

    @OneToOne(mappedBy = "localWasInfo")
    private IntegratedErrorLog integratedErrorLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

}
