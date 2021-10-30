package kr.ac.seowon.media.studentadminsite.domain;

import kr.ac.seowon.media.studentadminsite.repository.student.SiteInfoRespository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@DataJpaTest
@Transactional
class SiteInfoDtoTest {

    @Autowired
    SiteInfoRespository siteInfoRespository;

    @Test
    @DisplayName("siteInfo Test")
    void start_1() throws Exception{
        SiteInfo siteInfo1 = SiteInfo.createSiteInfo("name", "database");
        SiteInfo siteInfo2 = SiteInfo.createSiteInfo("name", "database");
        siteInfoRespository.save(siteInfo1);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private String DomainDuplicateException(HttpServletRequest request,DataIntegrityViolationException e) {
        System.out.println("request = " + request);
        return "Hello";
    }

}