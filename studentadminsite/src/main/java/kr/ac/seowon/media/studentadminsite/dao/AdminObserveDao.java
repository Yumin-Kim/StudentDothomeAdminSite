package kr.ac.seowon.media.studentadminsite.dao;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AdminObserveDao {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FullInfo {
        private List<AdminObserveDao.AdminObserveStudentInfo> infos = new ArrayList<>();
        //util
        private Integer currentPage;
        private Integer totalPage;
        private Integer currentCount;
        private Integer totalCount;

    }

    @Getter
    @Setter
    public static class AdminObserveStudentInfo {
        private Integer id;
        private String name;
        private Integer studentCode;
        private String email;
        private String phoneNumber;
        private Boolean inSchool;
        private Boolean isDeleted;
        private StudentDao.StudentSiteInfo siteInfo;
        private String adminName;

        public AdminObserveStudentInfo(Student student, SiteInfo siteInfo, Admin admin) {
            id = student.getId();
            name = student.getName();
            studentCode = student.getStudentCode();
            email = student.getEmail();
            phoneNumber = student.getPhoneNumber();
            inSchool = student.getInSchool();
            isDeleted = student.getIsDeleted();
            if (admin != null) {
                adminName = admin.getName();
            }
            if (siteInfo != null) {
                this.siteInfo = new StudentDao.StudentSiteInfo(siteInfo);
            }
        }
    }
}
