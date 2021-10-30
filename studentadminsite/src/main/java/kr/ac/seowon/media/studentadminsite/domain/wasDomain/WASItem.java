package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;


@Getter
public enum WASItem {
    SPRING("HTTP", 1), NODEJS("NODEJS", 2);

    private String message;
    private Integer order;

    WASItem(String message, Integer order) {
        this.message = message;
        this.order = order;
    }
}
