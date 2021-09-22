package kr.ac.seowon.media.studentadminsite.utils;

import com.jcraft.jsch.*;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.exception.utilexception.SSHException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SSHConnectionTest {


    private Session session = null;
    private Channel channel = null;
    private String sshusername = "user";
    private String sshhost = "172.17.0.1";
    private int sshPort = 22;
    private String sshPassword = "test1234";
    @Test
    @DisplayName("createDomain")
    void createDomain() throws Exception{
        final UtilConfigure utilConfigure = new UtilConfigure();
        utilConfigure.setSshusername("");
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(sshusername, sshhost, sshPort);
            session.setPassword(sshPassword);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            this.channel = session.openChannel("exec");
            try {
                ChannelExec channelExec = (ChannelExec) channel;
                channelExec.setCommand("echo $(ls /home)");
                ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                channel.setOutputStream(responseStream);
                channel.connect();
                while (channel.isConnected()) {
                    Thread.sleep(100);
                }
                String responseString = responseStream.toString();
                final List<String> strings = Arrays.asList(responseString.split(" "));
                System.out.println("responseString = " + responseString);
                strings.stream()
                        .forEach(System.out::println);

            } catch (JSchException | InterruptedException e) {
                throw new SSHException("ssh 접근하였지만 정보 수정에 실패하였습니다.");
            } finally {
                if (channel != null) {
                    channel.disconnect();
                }
                if (session != null) {
                    session.disconnect();
                }
            }
        } catch (JSchException e) {
            throw new SSHException("ssh 연결에 실패 하셨습니다.");
        }
    }

    @Test
    @DisplayName("deleteDomin")
    void deleteDomin() throws Exception{
        List<String> asd = List.of("asd", "qwe", "zxc");
        final String join = String.join(" ", asd);
        System.out.println("join = " + join);
    }

}