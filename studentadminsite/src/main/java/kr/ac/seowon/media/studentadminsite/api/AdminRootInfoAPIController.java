package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.SessionFactory;
import kr.ac.seowon.media.studentadminsite.dao.AdminDao;
import kr.ac.seowon.media.studentadminsite.dto.AdminReq;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.service.AdminRootService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/rootinfo")
@RequiredArgsConstructor
@Slf4j
public class AdminRootInfoAPIController {
    private final   AdminRootService adminRootService;
    private final   SessionFactory sessionFactory;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Res createAdmin(@Validated({AdminReq.createAdmin.class}) @RequestBody AdminReq.AdminDto adminDto) {
        AdminDao.BasicAdmin admin = adminRootService.createAdmin(adminDto);
        return Res.isOkWithData(admin, "admin 계정 생성을 성공하였습니다.");
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Res loginAdmin(
            @Valid @ModelAttribute AdminReq.AdminLoginDto adminLoginDto,
            HttpServletRequest request
    ) {

        sessionFactory.makeSession(request, "admin",adminLoginDto);
        AdminDao.BasicAdmin admin = adminRootService.loginAdmin(adminLoginDto);
        return Res.isOkWithData(admin, "해당 계정으로 로그인을 성공하였습니다.");
    }

    //TODO 세션 도는 jwt 도입하게 되면 다시 수정
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public Res logout(
            HttpServletRequest request
    ) {
        sessionFactory.removeSession(request,"admin");
        return Res.isOkByMessage("해당 계정으로 로그아웃을 성공하셨습니다.");
    }

    @PutMapping("/{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public Res modifyAdminInfo(
            HttpServletRequest request,
            @PathVariable("adminId") Integer adminId,
            @Validated({AdminReq.modifyAdmin.class}) @RequestBody AdminReq.AdminDto adminDto) {
        sessionFactory.validationSession(request, "admin");
        AdminDao.BasicAdmin modifyAdminInfo = adminRootService.modifyAdminInfo(adminId, adminDto);
        return Res.isOkWithData(modifyAdminInfo, "관리자의 정보를 수정 완료 했습니다.");
    }

    @GetMapping("/adminall")
    @ResponseStatus(HttpStatus.OK)
    public Res getAllPagingV1(
            HttpServletRequest request,
            Pageable pageable) {
        sessionFactory.validationSession(request, "admin");
        AdminDao.BasicPagingAdmin basicPagingAdmin = adminRootService.findAllPagingV1(pageable);
        return Res.isOkWithData(basicPagingAdmin, "총 관리자 조회 성공");
    }
}
