package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "docker_was_info")
public class DockerWasInfo extends WASIntegratedInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "docker_was_info_id")
    private Integer id;
    @Column(unique = true,nullable = false)
    private String nameOption;
    private String envOption;
    private String volumeOption;
    private String entrypointOption;
    @Column(unique = true,nullable = false)
    private String imageName;

    @Column(columnDefinition = "TEXT" , unique = true , nullable = false)
    private String dockerRunCommand;

    @OneToOne(mappedBy = "dockerWasInfo")
    private IntegratedErrorLog integratedErrorLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id" , nullable = false)
    private Student student;

    protected DockerWasInfo(Integer port, WASItem name, String applicationName) {
        super(port, name, applicationName);
    }

}
