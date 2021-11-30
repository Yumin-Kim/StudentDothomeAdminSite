package kr.ac.seowon.media.studentadminsite.repository.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.IntegratedErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegratedErrorLogRepository extends JpaRepository<IntegratedErrorLog,Integer> {

    @Query("select i from IntegratedErrorLog i  join fetch i.localWasInfo a where a.id = :id ")
    Optional<IntegratedErrorLog> findJoinWasId(@Param("id") Integer wasInfoId);

}
