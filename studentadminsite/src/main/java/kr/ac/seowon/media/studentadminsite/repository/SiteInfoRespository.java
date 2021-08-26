package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteInfoRespository extends JpaRepository<SiteInfo,Integer> {
    Optional<SiteInfo> findByDomainName(String domainName);
}
