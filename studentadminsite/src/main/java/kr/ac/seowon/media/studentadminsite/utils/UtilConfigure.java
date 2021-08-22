package kr.ac.seowon.media.studentadminsite.utils;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.beans.ConstructorProperties;

@Component
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
@ToString
public class UtilConfigure {
    private String sshusername;
    private String sshhost;
    private int sshPort;
    private String sshPassword;
    private String databaseUrl;
    private String databaseId;
    private String databasePwd;

}
