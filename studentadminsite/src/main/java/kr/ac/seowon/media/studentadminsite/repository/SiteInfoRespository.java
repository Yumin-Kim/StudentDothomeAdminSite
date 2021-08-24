package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteInfoRespository extends JpaRepository<SiteInfo,Integer> {
}
