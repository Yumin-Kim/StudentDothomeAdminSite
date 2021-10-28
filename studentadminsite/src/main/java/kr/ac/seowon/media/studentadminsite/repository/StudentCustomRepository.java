package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentCustomRepository {

    Page<Student> searchEqualsConditionInfoV1(Pageable pageable, AdminObserveReq.SearchCondition condition);

    Page<Student> searchSimilarConditionInfoV1(Pageable pageable, AdminObserveReq.SearchCondition condition);

}
