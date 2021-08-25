package kr.ac.seowon.media.studentadminsite.utils;

import com.jcraft.jsch.*;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.utilexception.SSHException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
// 해당 로직은 ssh 로 사용자 , 데이터 베이스 , 디렉토리가 모두 구성 되어 있다 전제하고 진행한다.
// 데이터 베이스는 사용자 이름과 비밀번호가 지정되어 있다.
@Slf4j
public class SSHConnection {

    private Session session = null;
    private Channel channel = null;

    @SneakyThrows(SSHException.class)
    public SSHConnection(UtilConfigure utilConfigure) {
        log.info(utilConfigure.toString());
        log.info("new SSHConnection() create >>");
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(utilConfigure.getSshusername(), utilConfigure.getSshhost(), utilConfigure.getSshPort());
            session.setPassword(utilConfigure.getSshPassword());
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            log.info("open sftp channel");
            this.channel = session.openChannel("exec");
        } catch (JSchException e) {
            throw new SSHException("ssh 연결에 실패 하셨습니다.");
        }
    }

    //TODO SSH 프로토콜로 접근하여 데이터 베이스 명과 디렉토리 명 변경 로직,
    @SneakyThrows(SSHException.class)
    public void modifyStudentInfo(StudentReq.SiteInfoDto siteinfo) {
        try {
            // 8. 채널을 SSH용 채널 객체로 캐스팅한다
            ChannelExec channelExec = (ChannelExec) channel;
            log.info("==> Connected to ");
            if (!siteinfo.getDomainName().equals("") && !siteinfo.getDomainName().equals("")) {
                log.info("domain , database name change ");
                channelExec.setCommand("./util.sh " + siteinfo.getOriginDomain() + " " + siteinfo.getDomainName());
            }
            if (!siteinfo.getDomainName().equals("") && siteinfo.getDomainName().equals("")) {
                log.info("only domain name change ");
                channelExec.setCommand("./util.sh " + siteinfo.getOriginDomain() + " " + siteinfo.getDomainName());
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
            String responseString = responseStream.toString();
            log.info("responseString = {}", responseString);
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
    }

    //TODO 삭제후 로그가 안나오면 throw 구현 > 존재하지 않는 유저입니다.
    @SneakyThrows(SSHException.class)
    public void deleteDomainInfo(String domain){
        log.info("deleteDomainInfo ");
        try {
            ChannelExec channelExec = (ChannelExec) channel;
            log.info("delete ssh,domain info");
            channelExec.setCommand("./deleteStudent.sh " + domain);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            String responseString = responseStream.toString();
            log.info("response String = {}", responseString);
            if(!responseString.contains("Done")){
                throw new StudentException("존재하지 않는 도메인 정보입니다.");
            }
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
    }

    public void createDomain(String domainName , String password) {
        log.info("createDomainInfo ");
        try {
            ChannelExec channelExec = (ChannelExec) channel;
            log.info("createDomain ssh,domain info");
            channelExec.setCommand("./createStudent.sh " + domainName + " " + password);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            String responseString = responseStream.toString();
            log.info("response String = {}", responseString);
            if(!responseString.contains("True")){
                throw new StudentException("존재하지 않는 도메인 정보입니다.");
            }
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
    }
//    // 1. JSch 객체를 생성한다.
//    JSch jsch = new JSch();
//    session = jsch.getSession(username, host, port);
//
//    // 3. 패스워드를 설정한다.
//            session.setPassword(password);
//
//    // 4. 세션과 관련된 정보를 설정한다.
//    java.util.Properties config = new java.util.Properties();
//    // 4-1. 호스트 정보를 검사하지 않는다.
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//
//    // 5. 접속한다.
//            session.connect();
//
//    // 6. sftp 채널을 연다.
//            log.info("open sftp channel");
//    channel = session.openChannel("exec");
//
//    // 8. 채널을 SSH용 채널 객체로 캐스팅한다
//    ChannelExec channelExec = (ChannelExec) channel;

}
