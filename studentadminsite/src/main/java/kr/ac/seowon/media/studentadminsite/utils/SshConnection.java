package kr.ac.seowon.media.studentadminsite.utils;

import com.jcraft.jsch.*;
import kr.ac.seowon.media.studentadminsite.dto.Req;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Slf4j
// 해당 로직은 ssh 로 사용자 , 데이터 베이스 , 디렉토리가 모두 구성 되어 있다 전제하고 진행한다.
// 데이터 베이스는 사용자 이름과 비밀번호가 지정되어 있다.
public class SshConnection {

    private  String username = "user";
    private String host = "172.17.0.2";
    private int port = 22;
    private String password = "test1234";
    //TODO SSH 프로토콜로 접근하여 데이터 베이스 명과 디렉토리 명 변경 로직,
    public void modifyStudentInfo(Req.SiteInfo siteinfo){
        Session session = null;
        Channel channel = null;
        log.info("try SSH connection");
        try {
            // 1. JSch 객체를 생성한다.
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);

            // 3. 패스워드를 설정한다.
            session.setPassword(password);

            // 4. 세션과 관련된 정보를 설정한다.
            java.util.Properties config = new java.util.Properties();
            // 4-1. 호스트 정보를 검사하지 않는다.
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 5. 접속한다.
            session.connect();

            // 6. sftp 채널을 연다.
            log.info("open sftp channel");
            channel = session.openChannel("exec");

            // 8. 채널을 SSH용 채널 객체로 캐스팅한다
            ChannelExec channelExec = (ChannelExec) channel;

            log.info("==> Connected to ={}",host);
            if (!siteinfo.getDomainName().equals("")  && !siteinfo.getDomainName().equals("")){
                log.info("domain , database name change ");
                channelExec.setCommand("./util.sh " + siteinfo.getOriginDomain() + " " + siteinfo.getDomainName());
            }
            if (!siteinfo.getDomainName().equals("") && siteinfo.getDomainName().equals("")){
                log.info("only domain name change ");
                channelExec.setCommand("./util.sh " + siteinfo.getOriginDomain() +" " + siteinfo.getDomainName() );
            }
            if (siteinfo.getDomainName().equals("") && !siteinfo.getDomainName().equals("")) {
                log.info("only database name change ");
            }
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            log.info("==> Connected to ={}",host);
            String responseString = responseStream.toString();
            log.info("responseString = {}",responseString);
        } catch (
                JSchException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
