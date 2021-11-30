package kr.ac.seowon.media.studentadminsite.repository.student;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static kr.ac.seowon.media.studentadminsite.domain.QAdmin.admin;
import static kr.ac.seowon.media.studentadminsite.domain.QSiteInfo.siteInfo;
import static kr.ac.seowon.media.studentadminsite.domain.QStudent.student;

public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    public StudentCustomRepositoryImpl(EntityManager em) {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageImpl searchEqualsConditionInfoV1(Pageable pageable, AdminObserveReq.SearchCondition condition) {
        QueryResults<Student> studentQueryResults = jpaQueryFactory.selectFrom(student)
                .leftJoin(student.siteInfo, siteInfo).fetchJoin()
                .leftJoin(student.admin, admin).fetchJoin()
                .where(searchConditionV1(condition))
                .orderBy(conditionSortingMethod(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(studentQueryResults.getResults(), pageable, studentQueryResults.getTotal());
    }

    @Override
    public Page<Student> searchSimilarConditionInfoV1(Pageable pageable, AdminObserveReq.SearchCondition condition) {
        QueryResults<Student> studentQueryResults = jpaQueryFactory.selectFrom(student)
                .leftJoin(student.siteInfo, siteInfo).fetchJoin()
                .leftJoin(student.admin, admin).fetchJoin()
                .where(searchContainConditionV1(condition))
                .orderBy(conditionSortingMethod(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(studentQueryResults.getResults(), pageable, studentQueryResults.getTotal());
    }

    private List<OrderSpecifier> conditionSortingMethod(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder pathBuilder = null;
            if (property.contains("domainName") || property.contains("databaseName")) {
                pathBuilder = new PathBuilder(Student.class, "siteInfo");
                property = "name";
            }
            else if (property.contains("adminName")) {
                pathBuilder = new PathBuilder(Admin.class, "admin");
            } else {
                pathBuilder = new PathBuilder(Student.class, "student");
            }
            boolean add = orders.add(new OrderSpecifier(direction, pathBuilder.get(property)));
        });
        return orders;
    }

    private BooleanBuilder searchConditionV1(AdminObserveReq.SearchCondition condition) {
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
        studentEntityBooleanPropertyEqQuery(condition, booleanBuilder);
        if (condition.getStudentCode() != null) {
            booleanBuilder.and(student.studentCode.eq(condition.getStudentCode()));
        }
        if (hasText(condition.getDomainName())) {
            booleanBuilder.and(siteInfo.domainName.eq(condition.getDomainName()));
        }
        return booleanBuilder;
    }

    private void studentEntityBooleanPropertyEqQuery(AdminObserveReq.SearchCondition condition, BooleanBuilder booleanBuilder) {
        if (condition.getInSchool() != null) {
            booleanBuilder.and(student.inSchool.eq(condition.getInSchool()));
        }
        if (condition.getIsDeleted() != null) {
            booleanBuilder.and(student.isDeleted.eq(condition.getIsDeleted()));
        }
    }

    private BooleanBuilder searchContainConditionV1(AdminObserveReq.SearchCondition condition) {
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
        studentEntityBooleanPropertyEqQuery(condition, booleanBuilder);
        if (condition.getStudentCode() != null) {
            booleanBuilder.and(student.studentCode.like(condition.getStudentCode() + "%"));
        }
        if (hasText(condition.getDomainName())) {
            booleanBuilder.and(siteInfo.domainName.contains(condition.getDomainName()));
        }
        return booleanBuilder;
    }

}
