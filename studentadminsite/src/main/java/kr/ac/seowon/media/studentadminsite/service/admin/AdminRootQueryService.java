package kr.ac.seowon.media.studentadminsite.service.admin;

import kr.ac.seowon.media.studentadminsite.dto.admin.AdminDtoRes;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.dto.admin.AdminReq;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.AdminException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminRootQueryService {

    private final AdminRepository adminRepository;

    public AdminDtoRes.BasicAdmin loginAdmin(AdminReq.AdminLoginDto adminLoginDto) {
        Admin findByAdmin = adminRepository.findByNameAndPassword(adminLoginDto.getName(), adminLoginDto.getPassword())
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_NOT_FOUND));
        return new AdminDtoRes.BasicAdmin(findByAdmin);
    }

    public AdminDtoRes.BasicPagingAdmin findAllPagingV1(Pageable pageable) {
        Page<Admin> findAdmins = adminRepository.findAll(pageable);
        List<AdminDtoRes.BasicAdmin> basicAdminList = findAdmins.getContent().stream()
                .map(AdminDtoRes.BasicAdmin::new)
                .collect(toList());
        return new AdminDtoRes.BasicPagingAdmin(basicAdminList, findAdmins.getNumber(), findAdmins.getTotalPages(), findAdmins.getSize(), (int) findAdmins.getTotalElements());
    }
}
