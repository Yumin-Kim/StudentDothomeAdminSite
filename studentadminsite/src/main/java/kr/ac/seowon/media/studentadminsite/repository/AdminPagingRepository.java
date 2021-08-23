package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.Admin;

import java.awt.print.Pageable;
import java.util.List;

public interface AdminPagingRepository {

    List<Admin> findAllPagingV1(Pageable pageable);
}
