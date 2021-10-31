package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import kr.ac.seowon.media.studentadminsite.domain.BaseEntity;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "integrated_error_log")
public class IntegratedErrorLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "integrated_error_log_id")
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String errorLogs;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_was_info_id")
    private LocalWasInfo localWasInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docker_was_info_id")
    private DockerWasInfo dockerWasInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "student_id")
    private Student student;
}
