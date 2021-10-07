package kr.ac.seowon.media.studentadminsite.service.admin;

import kr.ac.seowon.media.studentadminsite.dao.AdminDao;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.dto.AdminReq;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.AdminException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminRootCommandService {

    private final AdminRepository adminRepository;

    //TODO HashCode 생성 알고리즘 추가
    //TODO admin password 암호화 하여 다시 삽입

    /**
     * 관리자 생성
     * @param adminDto
     * @return
     */
    public AdminDao.BasicAdmin createAdmin(AdminReq.AdminDto adminDto) {
        Admin admin = adminDto.toEntity();
        List<Admin> admins = (List<Admin>) adminRepository.findByName(admin.getName())
                .map(data -> {
                    if (hasText(data.getName())) {
                        throw new AdminException("존재하는 이름입니다.");
                    }
                    return null;
                })
                .orElseGet(() -> adminRepository.findByHashCode(admin.getHashCode())
                        .stream()
                        .filter(admin1 -> admin1.getHashCode().equals(admin.getHashCode()))
                        .collect(toList()));
        if (admins.size() != 0) throw new AdminException("존재하는 HashCode입니다.");
        Admin save = adminRepository.save(admin);
        return new AdminDao.BasicAdmin(save);
    }

    public AdminDao.BasicAdmin modifyAdminInfo(Integer adminId, AdminReq.AdminDto adminDto) {
        Admin findAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException("존재하지 않는 admin 입니다."));
        findAdmin.modifyEntity(adminDto);
        return new AdminDao.BasicAdmin(findAdmin);
    }



}
