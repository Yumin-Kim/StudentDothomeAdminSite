package kr.ac.seowon.media.studentadminsite.dao;

import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import lombok.Getter;
import lombok.Setter;

public class StudentDao {
    @Getter
    @Setter
    public static class BasicStudent {
        private Integer id;
        private String name;
        private Integer StudentCode;
        private String phoneNumber;
        private String email;
        private Boolean inSchool;
        private Boolean isDeleted;
        private StudentSiteInfo siteInfo;

        public BasicStudent(Student student) {
            id = student.getId();
            name = student.getName();
            StudentCode = student.getStudentCode();
            if (student.getPhoneNumber() != null) {
                phoneNumber = student.getPhoneNumber();
            }
            if (student.getEmail() != null) {
                email = student.getEmail();
            }
            if (student.getInSchool() !=null) {
                inSchool = student.getInSchool();
            }
            if (student.getIsDeleted() != null) {
                isDeleted = student.getIsDeleted();
            }
            if (student.getSiteInfo() != null) {
                siteInfo = new StudentSiteInfo(student.getSiteInfo());
            }
        }
    }

    @Getter
    @Setter
    public static class DefaultStudent{
        private String name;
        private Integer studentCode;


        public DefaultStudent(Student student) {
            name = student.getName();
            studentCode = student.getStudentCode();
        }
    }

    @Getter
    @Setter
    public static class StudentSiteInfo {
        private String domainName;
        private String databaseName;

        public StudentSiteInfo(SiteInfo siteInfo) {
            if (siteInfo.getDomainName() != null) {
                domainName = siteInfo.getDomainName();
            }
            if (siteInfo.getDatabaseName() != null) {
                databaseName = siteInfo.getDatabaseName();
            }
        }
    }

}
