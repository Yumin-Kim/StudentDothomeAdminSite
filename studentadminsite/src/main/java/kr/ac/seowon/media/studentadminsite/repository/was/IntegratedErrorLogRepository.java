package kr.ac.seowon.media.studentadminsite.repository.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.IntegratedErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegratedErrorLogRepository extends JpaRepository<IntegratedErrorLog,Integer> {
}
