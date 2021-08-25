package kr.ac.seowon.media.studentadminsite.service;

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

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminRootService {

    private final AdminRepository adminRepository;

    public AdminDao.BasicAdmin createAdmin(AdminReq.AdminDto adminDto) {
        //TODO HashCode 생성 알고리즘 추가
        //TODO admin password 암호화 하여 다시 삽입
        Admin admin = adminDto.toEntity();
        Admin saveAdmin = (Admin) adminRepository.findByName(admin.getName())
                .map(data -> {
                    if (hasText(data.getName())) {
                        throw new AdminException("존재하는 이름입니다.");
                    }
                    return null;
                })
                .orElse(adminRepository.save(admin));
        return new AdminDao.BasicAdmin(saveAdmin);
    }

    public AdminDao.BasicAdmin loginAdmin(AdminReq.AdminLoginDto adminLoginDto) {
        Admin findByAdmin = adminRepository.findByNameAndPassword(adminLoginDto.getName(), adminLoginDto.getPassword())
                .orElseThrow(() -> new AdminException("아이디와 비밀번호를 존재하지 않습니다."));
        return new AdminDao.BasicAdmin(findByAdmin);
    }


    public AdminDao.BasicAdmin modifyAdminInfo(Integer adminId, AdminReq.AdminDto adminDto) {
        Admin findAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException("존재하지 않는 admin 입니다."));
        findAdmin.modifyEntity(adminDto);
        return new AdminDao.BasicAdmin(findAdmin);
    }

    public AdminDao.BasicPagingAdmin findAllPagingV1(Pageable pageable) {
        Page<Admin> findAdmins = adminRepository.findAll(pageable);
        List<AdminDao.BasicAdmin> basicAdminList = findAdmins.getContent().stream()
                .map(AdminDao.BasicAdmin::new)
                .collect(toList());
        return new AdminDao.BasicPagingAdmin(basicAdminList,findAdmins.getNumber(), findAdmins.getTotalPages(), findAdmins.getSize(), (int) findAdmins.getTotalElements());
    }
}
