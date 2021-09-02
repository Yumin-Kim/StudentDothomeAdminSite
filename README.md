# 닷홈\_학생별 개설 사이트 관리 사이트

# 개발 순서

1. 아파치 X 톰캣 연동 X
2. 화면 설계
3. REST API 설계
4. 백엔드 개발
5. 프론트 개발\_ V1
6. release 진행
7. 프론트 개발 V2 UI 수정

**release하기 전 테스트는 docker를 활용하여 진행하고자 한다.**

---

[참고 자료 정리](https://www.notion.so/9ee41ae39b1749a59caf311f51fd0081)

# 1. 우분투 환경 apache2 X tomcat9 연동

기존 서버 컴퓨터는 apache2 와 php 를 통해서 동적 컨텐츠를 제공하고 있는 구조이다.
해당 구조를 tomcat를 통해 Spring을 사용하고자 아키텍처 구조를 아래와 같이 구성하였다.
기존 tomcat을 동작 시키게 되면 :8080 를 통해서 apache와 연관 없이 동작하는것을 볼 수 있게 되는데 이런 부분을 수정 하기 위해서 apache에 부가적인 설정을 해주어야한다.
아래 링크와 같이 mod_jk를 사용하게 되면 apache에서 tomcat를 사용할 수 있는 설정이 된다.
하지만 따라했지만 동작하지 않는다.
문제는 mod_jk의 worker.properties가 제대로 동작하지 않는것 같다.

**현재 포트 연결이 안되는 부분은 생략하고 다음 단계로 진행할 예정이다.
계속 해당 부분을 붙잡고 있는것은 시간 낭비!!**

[우분투 - 아파치 톰캣 연동](https://daisyleh.blogspot.com/2020/03/blog-post.html)

기존 사이트 구성
현재 학생의 계정 생성 시 DB와 생성된 계정에 따른 디렉토리가 생겨 사이트를 구성하여 호스팅 되도록 PHP 로 구현 되어 있다.

//기존 구현 방식 >> 방식 유지하는 방식으로 개발
SSH 프로토콜을 활용하여 유저 생성 및 디렉토리 , 데이터베이스 생성 되는 구조

//TODO
Spring을 사용하게 된다면 addUser.php가 동작 할 수 있도록 구현하거나 adduser.php에 생성된 정보를 Student table에 저장해야할 필요

//TODO
Spring

화면 설계 전 기존 php 소스코드를 수정하지 않고 진행하려 했으며 개발하는 계정 사이트를 이용하기 보다는 기존에 구성된 사이트를 쓸 여지가 많기 때문에 사용자를 관리 할 수 있는 사이트 먼저 개발하자라는 생각이 들어 아래와 같은 구현을 먼저 알아 보았다.

### 서브) docker 기반 기존 구성된 서버와 유사 구조 구성후 사용자 정보 관리

기존 소스 코드를 분석을 하였고 소스코드는 ssh를 명령어를 통해 사용자를 생성 및 php의 시스템 콜 호출이 되는것을 활용하여 디렉토리 생성 , Mysql DBS에 mysql을 활용하여 데이터 베이스 생성 및 간단한 설정 하게끔 설정되어 있다. 아래 링크를 통해 데이터 베이스를 생성하는 코드를 볼 수 있을 것이다.

[Mysql 데이터베이스란? 'user'테이블과 'db'테이블?](https://kyoe.tistory.com/54)

[MySQL 계정 생성 관리 및 권한설정](https://2dubbing.tistory.com/13)

[mysql.user 테이블](https://runebook.dev/ko/docs/mariadb/mysqluser-table/index)

기존 apache2 와 php mysql 연동이 되어 있기에 동일한 구조를 docker에 구축하였고 구축하는 부분에서는 큰 어려움 없었지만 디버깅에 큰 불편함을 느끼게 되었고 vim를 사용하는 부분이 조금 불편하였다. sftp를 사용하는 방향으로 개발 진행을 요한다.

진행하며 php로 mysql 접근에 어려움이 있었지만 문제는 mysql 설정에 있어 비밀번호 설정 수준이 높았고 기존 구성한 mysql 비밀번호가 설정 수준에 적합하지 않아 접근을 못하는 경우를 겪게 되었다. 에러 코드는 MySQL error: 'Access denied for user 'root'@'localhost'을 보게 되었고 검색을 통해 비밀번호를 삭제하고 비밀 번호 설정 수준을 낮게 잡게 되어 해결 하게 되었다.

[https://toytvstory.tistory.com/1617](https://toytvstory.tistory.com/1617)
[https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=abc2185&logNo=220294755812](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=abc2185&logNo=220294755812)
[https://junho85.pe.kr/1484](https://junho85.pe.kr/1484)
[https://stackoverflow.com/questions/55237257/mysql-validate-password-policy-unknown-system-variable](https://stackoverflow.com/questions/55237257/mysql-validate-password-policy-unknown-system-variable)

오류 수정 이후 php 코드 작성 부분에서 시간을 소모하였다
기존 코드에서 모듈형식으로 동작하기 위해서 작성한 코드를 외부 파일의 함수 호출하는 방식으로 작성하였지만 아직 에러에 대한 분기처리가 부족하다.

##### 톰캣 빌드 테스트

톰캣 빌드

1.  windows 환경에서 개발 진행
    java는 OS를 구애 받는 언어 이기 때문에 상관 X
2.  maven war jar 빌드 방법
    [https://hue9010.github.io/spring/springboot-war/](https://hue9010.github.io/spring/springboot-war/)
3.  windows tomcat 9.0.52에서 java 11 spring이 빌드가 안되었던 이유
    [https://sarc.io/index.php/forum/tips/2715-java-home](https://sarc.io/index.php/forum/tips/2715-java-home)
4.  tomcat 로깅 수정
5.  jar로 [spring.start.io](http://spring.start.io/) 생성시 serlize하는 코드 생성 필요
    [https://browndwarf.tistory.com/67](https://browndwarf.tistory.com/67)
6.  기본 루트
    ROOT.war로 아카이브 진행 [https://its-easy.tistory.com/4](https://its-easy.tistory.com/4) ,

        server.xml

7.  우분투 환경 설정

    ```powershell
    apt-get install openjdk-11-jdk
    java --version
    wget http://apache.mirror.cdnetworks.com/tomcat/tomcat-9/v9.0.52/bin/apache-tomcat-9.0.52.tar.gz
    tar -zxvf apache-tomcat-9.0.52.tar.gz
    mv apache-tomcat-9.0.52 tomcat9
    ```

###### 개발 편의성

docker ssh server 구성 후 SFTP를 통해 파일 업로드를 통해 개발 편의성 상승

```powershell
apt install openssh-server
service --status-all
netstat -nap// 포트 확인
//sftp를 통해 war파일 전송후
mv ROOT.war ~/../usr/share/tomcat9/webapps/
```

### 2021.08.19 ⇒Docker 환경에서 환경 구축 및 war 파일 배포까지 진행 완료

---

# 2. 화면 설계

[참고 자료](./SUB_README/imageGroup.md) . 이미지 자료 정리 참고

# 3. REST API 설계

[참고 자료](./SUB_README/RESTAPI.md) . RESTAPI 참고
추가적으로 php로 구현된 api를 호출해야할 가능성이 있기 때문에 아래 코드를 보고 작성

[Spring Boot - 외부 API Json 방식으로 호출 하기](https://r-0o0-j.tistory.com/132)

# 4. 백엔드 개발

Spring & JPA를 사용하여 개발시 우려 사항

- 기존 테이블을 jpa가 인식하여 쿼리를 실행하는 구조로 갈것인지
- 또는 JPA가 생성한 테이블을 활용하여

# 5. 프론트 개발\_ V1

# 6. release 진행

# 7. 프론트 개발 V2 UI 수정
