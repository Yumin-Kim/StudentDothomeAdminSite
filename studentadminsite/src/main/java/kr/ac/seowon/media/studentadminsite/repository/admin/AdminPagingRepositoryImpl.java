package kr.ac.seowon.media.studentadminsite.repository.admin;

import kr.ac.seowon.media.studentadminsite.domain.Admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.util.List;

public class AdminPagingRepositoryImpl implements AdminPagingRepository {

    @PersistenceContext
    EntityManager em;
    @Override
    public List<Admin> findAllPagingV1(Pageable pageable) {
        return null;
    }
}
