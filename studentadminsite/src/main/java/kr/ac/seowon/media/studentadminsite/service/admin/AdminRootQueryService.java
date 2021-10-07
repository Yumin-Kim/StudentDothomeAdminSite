package kr.ac.seowon.media.studentadminsite.service.admin;

import kr.ac.seowon.media.studentadminsite.dao.AdminDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.dto.AdminReq;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.AdminException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminRootQueryService {

    private final AdminRepository adminRepository;

    public AdminDao.BasicAdmin loginAdmin(AdminReq.AdminLoginDto adminLoginDto) {
        Admin findByAdmin = adminRepository.findByNameAndPassword(adminLoginDto.getName(), adminLoginDto.getPassword())
                .orElseThrow(() -> new AdminException("아이디와 비밀번호를 존재하지 않습니다."));
        return new AdminDao.BasicAdmin(findByAdmin);
    }

    public AdminDao.BasicPagingAdmin findAllPagingV1(Pageable pageable) {
        Page<Admin> findAdmins = adminRepository.findAll(pageable);
        List<AdminDao.BasicAdmin> basicAdminList = findAdmins.getContent().stream()
                .map(AdminDao.BasicAdmin::new)
                .collect(toList());
        return new AdminDao.BasicPagingAdmin(basicAdminList, findAdmins.getNumber(), findAdmins.getTotalPages(), findAdmins.getSize(), (int) findAdmins.getTotalElements());
    }
}
