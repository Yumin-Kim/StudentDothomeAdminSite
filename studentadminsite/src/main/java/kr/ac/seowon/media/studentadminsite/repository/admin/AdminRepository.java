package kr.ac.seowon.media.studentadminsite.repository.admin;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer>,AdminPagingRepository {
    Optional<Admin> findByName(String name);

    Optional<Admin> findByNameAndPassword(String name, String password);

    Optional<Admin> findByHashCode(String hashCode);
}
