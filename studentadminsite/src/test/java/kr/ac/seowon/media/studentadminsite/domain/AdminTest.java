package kr.ac.seowon.media.studentadminsite.domain;

import lombok.Data;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class AdminTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("테스트 코드 ")
    void start_1() throws Exception{
        //given
        Admin admin = Admin.createAdmin("name","password","hashCode","01012341234");
        //when
        em.persist(admin);
        //then
    }

}