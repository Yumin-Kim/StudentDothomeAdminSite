package kr.ac.seowon.media.studentadminsite.service;

import kr.ac.seowon.media.studentadminsite.dao.AdminDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.dto.AdminReq;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.AdminException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Disabled
class AdminRootServiceTest {

    @Mock
    AdminRepository adminRepository;

    @InjectMocks
    AdminRootService adminRootService;

    AdminReq.AdminDto adminDto = new AdminReq.AdminDto("admin", "asdasd", "0123123123", "password");
    Admin admin = Admin.createAdmin("name", "0123123123", "asdasd", "password");

    @Test
    @DisplayName("admin 계정 생성 pass")
    void adminservice_1() throws Exception {
        //given
        given(adminRepository.findByName(any()))
                .willReturn(Optional.empty());
        given(adminRepository.save(any()))
                .willReturn(admin);
        given(adminRepository.findByHashCode(any()))
                .willReturn(Optional.empty());
        //when
        AdminDao.BasicAdmin createAdmin = adminRootService.createAdmin(adminDto);
        //then
        assertEquals(createAdmin.getName(), admin.getName());
        assertEquals(createAdmin.getHashCode(), admin.getHashCode());
    }

    @Test
    @DisplayName("admin 계정 생성시 중복 로직")
    @Disabled
    void adminservice_2() throws Exception {
        //given
        given(adminRepository.findByName(any()))
                .willReturn(Optional.of(admin))
                .willReturn(Optional.empty());
        given(adminRepository.save(any()))
                .willReturn(admin);
        given(adminRepository.findByHashCode(any()))
                .willReturn(Optional.of(admin));
        //when
        //then
        assertThrows(AdminException.class, () -> adminRootService.createAdmin(adminDto));
        assertThrows(AdminException.class, () -> adminRootService.createAdmin(adminDto));
    }

    @Test
    @DisplayName("admin 계정 수정 pass exception 로직 포함")
    void adminsService_1_modify() throws Exception {
        //given
        AdminReq.AdminDto adminDto = new AdminReq.AdminDto("superUser", null, null, null);
        given(adminRepository.findById(any()))
                .willReturn(Optional.of(admin))
                .willReturn(Optional.empty());
        //when
        AdminDao.BasicAdmin modifyAdminInfo = adminRootService.modifyAdminInfo(1, adminDto);
        //then
        assertEquals(modifyAdminInfo.getName(), "superUser");
        assertEquals(modifyAdminInfo.getHashCode(), "asdasd");
        assertThrows(AdminException.class, () -> adminRootService.modifyAdminInfo(2, adminDto));
    }

    @Test
    @DisplayName("admin paging")
    void adminService_paging() throws Exception {
        //given
        List<Admin> admins = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Admin admin = Admin.createAdmin("admin" + i, "qweqwe", "asdasd", "123123");
            admins.add(admin);
        }
        Page<Admin> adminPagingMock = new PageImpl<>(admins);
        PageRequest pagingAdminParam = PageRequest.of(0, 20);
//        Page<Admin> findAllPagingAdmin =
        given(adminRepository.findAll(pagingAdminParam))
                .willReturn(adminPagingMock);
        //when
        AdminDao.BasicPagingAdmin allPagingV1 = adminRootService.findAllPagingV1(pagingAdminParam);
        //then
//        System.out.println("allPagingV1.getCurrentCount() = " + allPagingV1.getCurrentCount());
//        System.out.println("allPagingV1.getTotalPage() = " + allPagingV1.getTotalPage());
//        System.out.println("allPagingV1.getCurrentPage() = " + allPagingV1.getCurrentPage());
//        System.out.println("allPagingV1.getTotalCount() = " + allPagingV1.getTotalCount());
    }

}