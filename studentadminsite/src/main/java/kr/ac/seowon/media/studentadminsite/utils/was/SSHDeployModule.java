package kr.ac.seowon.media.studentadminsite.utils.was;

import com.jcraft.jsch.*;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.utilexception.SSHException;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class SSHDeployModule {
    private final UtilConfigure utilConfigure;

    private Session session = null;
    private Channel channel = null;

    private void sshConfigure(String studentSSHName, String studentPassword){
        log.info("new sshConfigure() create >>");
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(studentSSHName, utilConfigure.getSshhost(), utilConfigure.getSshPort());
            session.setPassword(studentPassword);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            log.info("open sftp channel");
            this.channel = session.openChannel("exec");
        } catch (JSchException e) {
            throw new SSHException(ErrorCode.SSH_CONNECTION_ERROR);
        }
    }

    public void createGitWasInfo(String studentSSHName ,String studentPassword){
        sshConfigure(studentSSHName,studentPassword);
        try {
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand("./deleteStudent.sh  ");
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            String responseString = responseStream.toString();
            if(!responseString.contains("Done")){
                throw new SSHException(ErrorCode.SSH_DUPLICATE_DOMAIN);
            }
        } catch (JSchException | InterruptedException e) {
            throw new SSHException(ErrorCode.SSH_CONNECTION_ERROR);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }



}
