package kr.ac.seowon.media.studentadminsite.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.seowon.media.studentadminsite.domain.*;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static kr.ac.seowon.media.studentadminsite.domain.QAdmin.admin;
import static kr.ac.seowon.media.studentadminsite.domain.QSiteInfo.siteInfo;
import static kr.ac.seowon.media.studentadminsite.domain.QStudent.student;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.StringUtils.hasText;

@DataJpaTest
@Transactional
@Rollback(value = false)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AdminRepository adminRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void configure() {
        AdminObserveReq.BasicStudentDto basicStudentDto1 = new AdminObserveReq.BasicStudentDto("김유민", 201510302);
        AdminObserveReq.BasicStudentDto basicStudentDto2 = new AdminObserveReq.BasicStudentDto("김상민", 201510301);
        Admin admin = Admin.createAdmin("name1", "1234", "asd", "asd");
        Admin admin1 = Admin.createAdmin("name2", "1234", "asd", "asd");
        Student student1 = Student.createStudent(basicStudentDto1, admin);
        Student student2 = Student.createStudent(basicStudentDto2, admin);
        adminRepository.save(admin1);
        adminRepository.save(admin);
        studentRepository.save(student1);
        studentRepository.save(student2);
        for (int i = 0; i < 100; i++) {
            //도메인 데이터 베이스 학번 전화 번호
            AdminObserveReq.BasicStudentDto loopcreateDto = new AdminObserveReq.BasicStudentDto("yumin" + i, 201632323 + i);
            AdminObserveReq.BasicStudentDto loopcreateDto1 = new AdminObserveReq.BasicStudentDto("yumin" + i + 10, 201632323 + i + 1000);
            StudentReq.StudentDto studentDto = new StudentReq.StudentDto(loopcreateDto.getName(), loopcreateDto.getStudentCode(), "qweqwe" + i, "dbals" + i + "@qwe.com", "qwe", "0102365" + i);
            Student student3 = Student.createStudent(loopcreateDto, admin);
            Student student4 = Student.createStudent(loopcreateDto1, admin1);
            SiteInfo siteInfo = SiteInfo.createSiteInfo("domain" + i, "s" + loopcreateDto.getStudentCode());
            student3.modifyStudent(studentDto, admin, siteInfo);
            em.persist(siteInfo);
            em.persist(student3);
            em.persist(student4);
            student4.disabledStudent(true);

        }
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("StudentRepository QueryMethod")
    void start_1() throws Exception {
        //given
        //when
//        Student student = studentRepository.findByStudentCodeAndName(201610302, "").get();
        List<Student> findStudents = studentRepository.findByStudentCodeIn(List.of(201610302, 201610301));
        List<Student> byIdIn = studentRepository.findByIdIn(List.of(1, 100));
        //then
//        assertEquals(student.getStudentCode(), 201610302);
//        assertEquals(student.getInSchool(), true);
//        assertEquals(student.getIsDeleted(), false);
        assertEquals(findStudents.size(), 2);
        assertEquals(byIdIn.size(), 1);

    }

    /**
     * 검색 조건 정의후에 개발
     * /////////////////////
     * 검색 조건 : 이름 도메인 데이터 베이스 휴학여부 비활성화 관리자 학번 전화번호
     * 조건 : 완벽하게 일치 시 아니면 like 사용해서 진행할지
     * 조건 : 정렬 기준 ASC,DESC
     * 조건 : 페이징을 조건 사용할 것인지
     * 고유한 값 도메인 데이터 베이스 학번 전화 번호
     * /////////////////////
     * like 사용하는 경우
     * 복수 건 : 이름 , 관리자 , 도메인 , 데이터 베이스 , 학번 , 전화번호
     * 완벽한 일치시만 가능
     * 휴학여부 , 비활성화
     * /////////////////////
     * 완벽한 일치할 시
     * 단건 : 도메인 , 데이터 베이스 , 학번 , 전화번호
     * 복수 건 : 이름 , 휴학여부 , 비활성화 , 관리자
     */
    @Test
    @DisplayName("조건에 완벽하게 일치할때 동작")
    void search_current_correct() throws Exception {
        //given
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        AdminObserveReq.SearchCondition condition = AdminObserveReq.SearchCondition.builder()
                .AdminName(null)
                .studentCode(null)
                .domainName(null)
                .email(null)
                .inSchool(true)
                .isDeleted(null)
                .name(null)
                .phoneNumber(null)
                .databaseName(null)
                .build();
        //when
        List<Student> fetch = jpaQueryFactory.selectFrom(student)
                .where(searchConditionV2(condition))
                .leftJoin(student.admin, admin)
                .leftJoin(student.siteInfo, siteInfo)
                .fetch();

        int size = fetch.size();
        System.out.println("size = " + size);

        System.out.println("================================================");

        Sort.Order sorting1 = new Sort.Order(Sort.Direction.DESC, "siteInfo.domainName");
        Sort.Order sorting2 = new Sort.Order(Sort.Direction.DESC, "name");

        PageRequest name = PageRequest.of(0, 5, Sort.by(List.of(sorting1,sorting2)));
        QueryResults<Student> studentQueryResults = jpaQueryFactory.selectFrom(student)
                .where(searchConditionV2(condition))
                .leftJoin(student.admin, admin)
                .leftJoin(student.siteInfo, siteInfo).fetchJoin()
                .offset(name.getOffset())
                .limit(name.getPageSize())
                .orderBy(getOrderSpecifier(name.getSort()).stream().toArray(OrderSpecifier[]::new))
                .fetchResults();
        //then

        List<Student> results = studentQueryResults.getResults();
        long limit = studentQueryResults.getLimit();
        long offset = studentQueryResults.getOffset();
        long total = studentQueryResults.getTotal();
        System.out.println("limit = " + limit);
        System.out.println("total = " + total);
        System.out.println("offset = " + offset);
        System.out.println("results.size() = " + results.size());
        
        results.stream()
                .forEach(student1 -> {
                    System.out.println("student1.getName() = " + student1.getName());
                    System.out.println("student1.getSiteInfo() = " + student1.getSiteInfo().getDomainName());
                });

    }

    @Test
    @DisplayName("동적 쿼리 like")
    void start_DynamicQuery_like_outerJoin() throws Exception {
        //given
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        AdminObserveReq.SearchCondition condition = AdminObserveReq.SearchCondition.builder()
                .AdminName(null)
                .studentCode(201632)
                .domainName(null)
                .email(null)
                .inSchool(null)
                .isDeleted(null)
                .name("yumin1")
                .phoneNumber(null)
                .databaseName(null)
                .build();
        //when
        List<Student> fetch = jpaQueryFactory.selectFrom(student)
                .leftJoin(student.admin, admin)
                .leftJoin(student.siteInfo, siteInfo)
                .where(searchContainConditionV1(condition))
                .fetch();
        int size = fetch.size();
        System.out.println("size = " + size);
        //then
    }

    /**
     * yumin으로 시작하는 사람 중 사이트가 null값 확인
     * @throws Exception
     */
    @Test
    @DisplayName("서브 쿼리를 통해 null 값도 가지고 오도록")
    void check_sub_query_null() throws Exception{
        //given
        PageRequest of = PageRequest.of(0, 10);
        Page<Student> all = studentRepository.findAll(of);
        List<Student> name = em.createQuery("select s from Student s " +
                "left join fetch s.siteInfo si " +
                "where s.name like :name ", Student.class)
                .setParameter("name", "yumin%")
                .getResultList();
        name.stream()
                .forEach(student1 -> {
                    System.out.println("student1.getName() = " + student1.getName());
                    System.out.println("student1.getSiteInfo().getDomainName() = " + student1.getSiteInfo());
                });
        all.stream()
                .forEach(student1 -> {
                    System.out.println("student1.getName() = " + student1.getName());
                    System.out.println("student1.getSiteInfo().getDomainName() = " + student1.getSiteInfo());
                    if (student1.getSiteInfo() != null) {
                        System.out.println("null 값이 아닙니다.");
                        System.out.println("student1.getSiteInfo().getDomainName() = " + student1.getSiteInfo().getDomainName());

                    }
                });
        //when

        //then
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        // Sort
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Student.class, "student");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        return orders;
    }

    private BooleanBuilder searchConditionV2(AdminObserveReq.SearchCondition condition) {
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

    private void extracted(AdminObserveReq.SearchCondition condition, BooleanBuilder booleanBuilder) {
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
        extracted(condition, booleanBuilder);
        if (condition.getStudentCode() != null) {
            booleanBuilder.and(student.studentCode.like(condition.getStudentCode()+"%"));
        }
        if (hasText(condition.getDomainName())) {
            booleanBuilder.and(siteInfo.domainName.contains(condition.getDomainName()));
        }
        return booleanBuilder;
    }

    private BooleanExpression searchConditionV1(AdminObserveReq.SearchCondition condition) {
        return phoneNumberEqualCond(condition.getPhoneNumber())
//                .and(nameEqualCond(condition.getName()))
//                .and(isDeletedEqualCond(condition.getIsDeleted()))
//                .and(inSchoolEqualCond(condition.getInSchool()))
//                .and(emailEqualCond(condition.getEmail()))
//                .and(dataNameEqualCond(condition.getDatabaseName()))
//                .and(domainNameEqualCond(condition.getDomainName()))
                .and(adminNameEqualCond(condition.getAdminName()))
                .and(studentCodeEqualCond(condition.getStudentCode()));
    }

    private BooleanExpression phoneNumberEqualCond(String phoneNumber) {
        return phoneNumber != null ? student.phoneNumber.eq(phoneNumber) : null;
    }

    private BooleanExpression nameEqualCond(String name) {
        return name != null ? student.name.eq(name) : null;
    }

    private BooleanExpression isDeletedEqualCond(Boolean inSchool) {
        return inSchool != null ? student.inSchool.eq(inSchool) : null;
    }

    private BooleanExpression inSchoolEqualCond(Boolean inSchool) {
        return inSchool != null ? student.inSchool.eq(inSchool) : null;
    }

    private BooleanExpression emailEqualCond(String email) {
        return email != null ? student.email.eq(email) : null;
    }

    private BooleanExpression dataNameEqualCond(String databaseName) {
        return databaseName != null ? siteInfo.databaseName.eq(databaseName) : null;
    }

    private BooleanExpression domainNameEqualCond(String domainName) {
        return domainName != null ? siteInfo.domainName.eq(domainName) : null;
    }

    private BooleanExpression adminNameEqualCond(String adminName) {
        return adminName != null ? admin.name.eq(adminName) : null;
    }

    private BooleanExpression studentCodeEqualCond(Integer studentCode) {
        return studentCode != null ? student.studentCode.eq(studentCode) : null;
    }

}