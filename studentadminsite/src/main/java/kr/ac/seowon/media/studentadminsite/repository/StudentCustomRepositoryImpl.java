package kr.ac.seowon.media.studentadminsite.repository;


import com.querydsl.core.BooleanBuilder;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;
import static kr.ac.seowon.media.studentadminsite.domain.QAdmin.admin;
import static kr.ac.seowon.media.studentadminsite.domain.QSiteInfo.siteInfo;
import static kr.ac.seowon.media.studentadminsite.domain.QStudent.student;
public class StudentCustomRepositoryImpl implements StudentCustomRepository {
    @PersistenceContext
    EntityManager em;


    @Override
    public Optional<Student> savec(Student student) {
        em.persist(student);

        Student student1 = em.find(Student.class, student.getId());
        return Optional.ofNullable(student1);
    }


    private BooleanBuilder searchConditionV2(AdminObserveReq.SearachCondition condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (hasText(condition.getPhoneNumber())) {
            booleanBuilder.and(student.phoneNumber.eq(condition.getPhoneNumber()));
        }
        if (hasText(condition.getName())) {
            booleanBuilder.and(student.name.eq(condition.getName()));
        }
        if (hasText(condition.getAdminName())) {
            booleanBuilder.and(admin.name.eq(condition.getAdminName()));
        }
        if (hasText(condition.getEmail())) {
            booleanBuilder.and(student.email.eq(condition.getEmail()));
        }
        if (hasText(condition.getDatabaseName())) {
            booleanBuilder.and(siteInfo.databaseName.eq(condition.getDatabaseName()));
        }
        extracted(condition, booleanBuilder);
        if (condition.getStudentCode() != null) {
            booleanBuilder.and(student.studentCode.eq(condition.getStudentCode()));
        }
        if (hasText(condition.getDomainName())) {
            booleanBuilder.and(siteInfo.domainName.eq(condition.getDomainName()));
        }
        return booleanBuilder;
    }

    private void extracted(AdminObserveReq.SearachCondition condition, BooleanBuilder booleanBuilder) {
        if (condition.getInSchool() != null) {
            booleanBuilder.and(student.inSchool.eq(condition.getInSchool()));
        }
        if (condition.getIsDeleted() != null) {
            booleanBuilder.and(student.isDeleted.eq(condition.getIsDeleted()));
        }
    }

    private BooleanBuilder searchContainConditionV1(AdminObserveReq.SearachCondition condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (hasText(condition.getPhoneNumber())) {
            booleanBuilder.and(student.phoneNumber.contains(condition.getPhoneNumber()));
        }
        if (hasText(condition.getName())) {
            booleanBuilder.and(student.name.contains(condition.getName()));
        }
        if (hasText(condition.getAdminName())) {
            booleanBuilder.and(admin.name.contains(condition.getAdminName()));
        }
        if (hasText(condition.getEmail())) {
            booleanBuilder.and(student.email.contains(condition.getEmail()));
        }
        if (hasText(condition.getDatabaseName())) {
            booleanBuilder.and(siteInfo.databaseName.contains(condition.getDatabaseName()));
        }
        extracted(condition, booleanBuilder);
        if (condition.getStudentCode() != null) {
            booleanBuilder.and(student.studentCode.like(condition.getStudentCode()+"%"));
        }
        if (hasText(condition.getDomainName())) {
            booleanBuilder.and(siteInfo.domainName.contains(condition.getDomainName()));
        }
        return booleanBuilder;
    }

}
