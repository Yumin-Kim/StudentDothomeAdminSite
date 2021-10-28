package kr.ac.seowon.media.studentadminsite.dto.adminobserve;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.SiteInfo;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.student.StudentDtoRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AdminObserveDtoRes {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FullInfo {
        private List<AdminObserveDtoRes.AdminObserveStudentInfo> infos = new ArrayList<>();
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
        private StudentDtoRes.StudentSiteInfo siteInfo;
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
                this.siteInfo = new StudentDtoRes.StudentSiteInfo(siteInfo);
            }
        }
    }
}
