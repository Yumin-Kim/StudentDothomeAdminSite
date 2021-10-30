package kr.ac.seowon.media.studentadminsite.dto.was;

import kr.ac.seowon.media.studentadminsite.domain.wasDomain.DeployMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class WasInfoRes {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LocalWasTestResultRes{
        private DeployMethod deployMethod;
    }

}
