package kr.ac.seowon.media.studentadminsite.domain.wasDomain;

import javax.persistence.Enumerated;


public enum WASItem {
    SPRING("java 애플리케이션", 1), NODEJS("NodeJS 애플리케이션", 2);

    private String message;
    private Integer order;

    WASItem(String message, Integer order) {
        this.message = message;
        this.order = order;
    }
}
