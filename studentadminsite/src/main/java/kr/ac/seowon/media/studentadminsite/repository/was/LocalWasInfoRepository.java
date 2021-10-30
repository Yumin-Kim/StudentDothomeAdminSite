package kr.ac.seowon.media.studentadminsite.repository.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.LocalWasInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalWasInfoRepository extends JpaRepository<LocalWasInfo,Integer> {
    Optional<LocalWasInfo> findByPort(Integer port);
}
