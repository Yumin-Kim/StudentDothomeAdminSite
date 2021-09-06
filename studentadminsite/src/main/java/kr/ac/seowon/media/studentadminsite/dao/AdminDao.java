package kr.ac.seowon.media.studentadminsite.dao;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import lombok.*;

import java.util.List;


public class AdminDao {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicAdmin{
        private String name;
        private String hashCode;
        private String phoneNumber;

        public BasicAdmin(Admin admin) {
            name = admin.getName();
            hashCode = admin.getHashCode();
            phoneNumber = admin.getPhoneNumber();
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class BasicPagingAdmin {
        private List<BasicAdmin> adminInfos;
        private Integer currentPage;
        private Integer totalPage;
        private Integer currentCount;
        private Integer totalCount;
    }
}