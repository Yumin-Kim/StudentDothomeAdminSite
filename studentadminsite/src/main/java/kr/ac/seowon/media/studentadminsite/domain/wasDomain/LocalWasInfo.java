package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.concurrent.atomic.LongAccumulator;

@Entity
@Getter
@Table(name = "local_was_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalWasInfo extends WASIntegratedInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_was_info_id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    private DeployMethod deployMethod;
    @Column(columnDefinition = "TEXT")
    private String githubLink;

    private String dirLocation;

    @OneToOne(mappedBy = "localWasInfo")
    private IntegratedErrorLog integratedErrorLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Builder
    protected LocalWasInfo(Integer port, WASItem name, DeployMethod deployMethod, String githubLink, String dirLocation, Student student, String applicationName) {
        super(port, name, applicationName);
        this.deployMethod = deployMethod;
        this.githubLink = githubLink;
        this.dirLocation = dirLocation;
        this.student = student;
    }

    protected LocalWasInfo(Integer port, WASItem name, String applicationName) {
        super(port, name, applicationName);
    }

    public static LocalWasInfo createEntity(Student student, Integer port, WASItem name, DeployMethod deployMethod, String applicationName, String dirLocation) {
        return LocalWasInfo.builder()
                .deployMethod(deployMethod)
                .name(name)
                .port(port)
                .student(student)
                .applicationName(applicationName)
                .dirLocation(dirLocation)
                .build();
    }


    public static LocalWasInfo createEntity(Student student, Integer port, WASItem name, DeployMethod deployMethod, String applicationName, String dirLocation, String githubLink) {
        return LocalWasInfo.builder()
                .dirLocation(dirLocation)
                .applicationName(applicationName)
                .student(student)
                .port(port)
                .deployMethod(deployMethod)
                .name(name)
                .githubLink(githubLink)
                .build();
    }
}
