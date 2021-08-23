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
        private String phoneNumber;
        private String email;
        private Boolean inSchool;
        private StudentSiteInfo studentSiteInfo;

        public BasicStudent(Student student) {
            id = student.getId();
            name = student.getName();
            phoneNumber = student.getPhoneNumber();
            email = student.getEmail();
            inSchool = student.getInSchool();
            studentSiteInfo = new StudentSiteInfo(student.getSiteInfo());
        }
    }

    @Getter
    @Setter
    public static class StudentSiteInfo {
        private String domainName;
        private String databaseName;

        public StudentSiteInfo(SiteInfo siteInfo) {
            domainName = siteInfo.getDomainName();
            databaseName = siteInfo.getDatabaseName();
        }
    }

}
