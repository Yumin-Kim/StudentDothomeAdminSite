package kr.ac.seowon.media.studentadminsite.dto.was;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

public class UserDatabaseErrorLog {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserConnectDto{
        private Integer wasInfoId;
        private String userDatabaseUrl;
    }

    @Getter
    @Setter
    @ToString
    public static  class UserWasErrorLogResultDto{
        private Integer id;
        private String errorLogs;
        private LocalDateTime createdAt;
        private LocalDateTime lastModified;
        private LocalDateTime deleted;
        private Boolean isDelete;
    }

}
