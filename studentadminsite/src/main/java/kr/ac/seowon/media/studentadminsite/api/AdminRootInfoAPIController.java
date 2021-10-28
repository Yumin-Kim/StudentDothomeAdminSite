package kr.ac.seowon.media.studentadminsite.api;

import kr.ac.seowon.media.studentadminsite.dto.admin.AdminDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.admin.AdminReq;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import kr.ac.seowon.media.studentadminsite.service.admin.AdminRootCommandService;
import kr.ac.seowon.media.studentadminsite.service.admin.AdminRootQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/rootinfo")
@RequiredArgsConstructor
@Slf4j
public class AdminRootInfoAPIController {
    private final AdminRootQueryService adminRootQueryService;
    private final AdminRootCommandService adminRootCommandService;
    private final SessionFactory sessionFactory;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Res createAdmin(@Validated({AdminReq.createAdmin.class}) @RequestBody AdminReq.AdminDto adminDto) {
        AdminDtoRes.BasicAdmin admin = adminRootCommandService.createAdmin(adminDto);
        return Res.isOkWithData(admin, "admin 계정 생성을 성공하였습니다.");
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Res loginAdmin(
            @Valid @ModelAttribute AdminReq.AdminLoginDto adminLoginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        AdminDtoRes.BasicAdmin admin = adminRootQueryService.loginAdmin(adminLoginDto);
        return Res.isOkWithData(admin, "해당 계정으로 로그인을 성공하였습니다.");
    }

    //TODO 세션 도는 jwt 도입하게 되면 다시 수정
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public Res logout(
            HttpServletRequest request
    ) {
        return Res.isOkByMessage("해당 계정으로 로그아웃을 성공하셨습니다.");
    }

    @PutMapping("/{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public Res modifyAdminInfo(
            HttpServletRequest request,
            @PathVariable("adminId") Integer adminId,
            @Validated({AdminReq.modifyAdmin.class}) @RequestBody AdminReq.AdminDto adminDto) {
        AdminDtoRes.BasicAdmin modifyAdminInfo = adminRootCommandService.modifyAdminInfo(adminId, adminDto);
        return Res.isOkWithData(modifyAdminInfo, "관리자의 정보를 수정 완료 했습니다.");
    }

    @GetMapping("/adminall")
    @ResponseStatus(HttpStatus.OK)
    public Res getAllPagingV1(
            HttpServletRequest request,
            Pageable pageable) {
        AdminDtoRes.BasicPagingAdmin basicPagingAdmin = adminRootQueryService.findAllPagingV1(pageable);
        return Res.isOkWithData(basicPagingAdmin, "총 관리자 조회 성공");
    }
}
