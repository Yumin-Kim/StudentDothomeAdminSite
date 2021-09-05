package kr.ac.seowon.media.studentadminsite.repository;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.dto.AdminReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void configure(){
        Admin admin1 = Admin.createAdmin("admin1", "qweqwe", "asdasdq", "123123");
        Admin admin2 = Admin.createAdmin("admin2", "qweqwe", "asdasdw", "123123");
        Admin admin3 = Admin.createAdmin("admin3", "qweqwe", "asdasde", "123123");
        adminRepository.save(admin1);
        adminRepository.save(admin2);
        adminRepository.save(admin3);
        for (int i = 0; i < 50; i++) {
            Admin admin = Admin.createAdmin("admin"+i+" "+i, "qweqwe", "asdasd", "123123");
            adminRepository.save(admin);
        }
    }

    @Test
    @DisplayName("Basic jpa Query Method >> findByName & findByNameAndPassword & admin modify")
    void adminRepo_queryMethod() throws Exception{
        //given
        AdminReq.AdminDto adminDto = new AdminReq.AdminDto("superUser", null, null, "qweqwe");
        String asdasdq = adminRepository.findByHashCode("asdasdq").get().getHashCode();
        System.out.println("findByHashCode = " + asdasdq);
        //when
        Admin findByNameAdmin = adminRepository.findByName("admin3").get();
        Admin findByNameAndPasswordAdmin = adminRepository.findByNameAndPassword("admin2", "qweqwe").get();
//        Admin findByIdAdmin = adminRepository.findById(1).get();
//        findByIdAdmin.modifyEntity(adminDto);
        //then
        assertEquals(findByNameAdmin.getName(),"admin3");
        assertEquals(findByNameAndPasswordAdmin.getName(),"admin2");
        assertEquals(findByNameAndPasswordAdmin.getPassword(),"qweqwe");
//        assertEquals(findByIdAdmin.getName(),"superUser");
//        assertEquals(findByIdAdmin.getPassword(),"qweqwe");
    }

    @Test
    @DisplayName("페이징 테스트")
    void paging() throws Exception{
        //given
        PageRequest of = PageRequest.of(0, 20);
        Page<Admin> all = adminRepository.findAll(of);
        System.out.println("all.getTotalElements() = " + all.getTotalElements());
        System.out.println("all.getTotalPages() = " + all.getTotalPages());
        System.out.println("all.getNumber() = " + all.getNumber());
        System.out.println("all.getNumberOfElements() = " + all.getNumberOfElements());
        System.out.println("all.getSize() = " + all.getSize());
        PageRequest sorting = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC,"name"));
        Page<Admin> resultSortingAdmin = adminRepository.findAll(sorting);
        //when
        resultSortingAdmin.getContent().stream()
                .forEach(admin -> System.out.println("admin.getName() = " + admin.getName()));
        //then
    }

}