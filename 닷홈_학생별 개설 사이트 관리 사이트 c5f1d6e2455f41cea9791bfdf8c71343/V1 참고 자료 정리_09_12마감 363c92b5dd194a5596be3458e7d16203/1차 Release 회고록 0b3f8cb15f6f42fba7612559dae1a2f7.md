# 1차 Release 회고록

생성일: 2021년 9월 11일 오후 1:03

## 🔷 8월 16일 ~ 9월 10일[3주차는 제외]

총 3주 반 정도의 시간을 들여 설계부터 개발 , 배포 까지 진행해보았다.
타인이 작성한 코드 및 환경을 기반으로  프로젝트를 설계하여 개발 배포하는 과정까지는 처음으로 시도해보았다.

---

### 🔹 프론트 개발 회고

- 프론트 디렉토리 구조

    ```
    .
    ├── index.html
    ├── multi
    │   ├── app.js
    │   └── index.html
    ├── package.json
    ├── package-lock.json
    ├── src
    │   ├── components
    │   │   ├── CommonTable.tsx
    │   │   ├── Description_ModalList.tsx
    │   │   ├── ExcelParingComponent.tsx
    │   │   ├── FormItemComponent.tsx
    │   │   ├── FormV2.tsx
    │   │   ├── InputBox.tsx
    │   │   ├── ModifiiedModal.tsx
    │   │   ├── RadiooBox.tsx
    │   │   └── SortingForm.tsx
    │   ├── index.tsx
    │   ├── layouts
    │   │   ├── Basic.tsx
    │   │   ├── Navigation.tsx
    │   │   └── style.tsx
    │   ├── pages
    │   │   ├── AdminPage
    │   │   ├── Home.tsx
    │   │   ├── MainInputPage.tsx
    │   │   └── StudentPage
    │   ├── redux_folder
    │   │   ├── actions
    │   │   ├── reducers
    │   │   ├── sagas
    │   │   └── store.ts
    │   └── types
    │       ├── actionParam.ts
    │       ├── action.ts
    │       ├── defultType.ts
    │       └── storeType.ts
    ├── tree.txt
    ├── tsconfig-for-webpack-config.json
    ├── tsconfig.json
    └── webpack.config.ts

    12 directories, 29 files
    ```

- **회고**
    - 기존 토이 프로젝트를 진행할때 보다는 설계나 테스트를 자주 진행하여 추가적으로 수정을 빈번하게 하지는 않았지만 프론트 개발간에 API를 수정하거나 제가 작성한 API문서(Swagger문서)가 올바르지 않아 코드를 직접 돌려 봐야지 Response값을 알아가는 경우도 빈번하게 있었다.
     [API 문서 미숙]
    - 또한 프론트 개발을 TypeScript 기반 React , state 관리를 위한 Redux, 비동기 요청을 기반 state 관리를 위해 Redux-Saga , Redux의 불편성을 유지하기 위한 Immer 라이브러리를 활용하여 개발을 진행하였는데 몇개월만에 라이브러리를 사용하게 되어 백엔드 API 개발 보다 몇배로 시간이 더 소모 되었고 TypeScript의 강타입을 잘 활용하지 않고 any타입을 난무 한것이 많이 아쉬웠다.
    [개발 기간에 따라 개발 언어 선택 하며 TypeScript사용간에 강타입을 사용해야한다.]
    - UI디자인 부분에서는 Antd css프레임 워크를 활용하여 개발하였는데 , 사용하는 편리함에 있어 최적화 부분에 있어 좋지 않은것 같다.
    [UI 라이브러리 사용 미숙 및 트레이드 오프 발생]
    - 향후 React를 활용하여 개발을 진행할 일이 있다면 기간을 통해 Typscript 또는 JavaScript의 유무를 판단해야할것이며 state관리를 위해 Recoil 또는 SWR 사용이 편리한 라이브러리를 사용해야할것 같다.

    - 현 프로젝트에서는 Redux를 활용하였는데 기존 JavaScript에서도 코드이 반복이 많지만 TypeScript를 활용하여 진행하다 보니 타입이나 코드 반복이 너무 심해져 유지 보수 측면에서는 좋지 않는것 같다.
    [React 개발에 있어 TypeScript 또는 JavaScript 선택 , State 관리를 위한 라이브러리 선택]
    - 백엔드API 연결간에 CORS 문제가 발생하여 Spring으로 간단히 해결을 해보았지만 Docker 기반 배포 환경과 유사하게 만들어서 배포하는 환경과 실 배포 환경간에 코드 수정하는 부분이 번거러움 제공하였다.
    [개발,배포환경에 따른 webpack 빌드 환경 구축 요구]
    - 프론트 개발에 있어 React를 사용해야할 만큼의 규모 , 개발 기간을 충분히 고려하여 개발을 진행해야하며 굳이 React가 필요하지 않다면 Webpack , Babel등 필요한 라이브러리만 활용하여 개발을 진행한다.
    [Spring의 Template engine 또는 React를 활용해야하는지 충분히 설계 및 기획 부분을 통해 선택 ]
    - React Component 코드 작성 간 미숙한 부분으로 인해 코드가 엉망으로 작성
    [Component 분리 및 Props 관리]
    - 동일 조건 , 유사 조건을 통해 조회하는 검색조건에서 sorting하는 UI가 제공 되지않고 있다
    - .nvmrc를 통해 node 버전 관리

---

### 🔹 백엔드 개발 회고

- 백엔드 디렉토리 구조

    ```
    .
    ├── api
    │   ├── AdminRootInfoAPIController.java
    │   ├── AdminStudentObserveAPIController.java
    │   ├── AdminUtilPermitController.java
    │   └── StudentAPIController.java
    ├── controller
    │   └── HelloController.java
    ├── dao
    │   ├── AdminDao.java
    │   ├── AdminObserveDao.java
    │   └── StudentDao.java
    ├── domain
    │   ├── Admin.java
    │   ├── BaseEntity.java
    │   ├── SiteInfo.java
    │   └── Student.java
    ├── dto
    │   ├── AdminObserveReq.java
    │   ├── AdminReq.java
    │   ├── Res.java
    │   └── StudentReq.java
    ├── exception
    │   ├── controllerexception
    │   │   ├── AdminObserveException.java
    │   │   └── InsertDuplicateException.java
    │   ├── CustomCollectionValidtion.java
    │   ├── domainexception
    │   │   ├── AdminException.java
    │   │   ├── StudentException.java
    │   │   └── StudentSiteInfoException.java
    │   ├── ExceptionAdvice.java
    │   └── utilexception
    │       ├── SSHException.java
    │       └── UtilJdbcConnectionException.java
    ├── JpaAuditingConfiguraion.java
    ├── repository
    │   ├── AdminPagingRepositoryImpl.java
    │   ├── AdminPagingRepository.java
    │   ├── AdminRepository.java
    │   ├── SiteInfoRespository.java
    │   ├── StudentCustomRepositoryImpl.java
    │   ├── StudentCustomRepository.java
    │   └── StudentRepository.java
    ├── service
    │   ├── AdminRootService.java
    │   ├── AdminStudentObserveService.java
    │   └── StudentService.java
    ├── ServletInitializer.java
    ├── StudentadminsiteApplication.java
    ├── SwaggerConfig.java
    ├── tree.txt
    ├── utils
    │   ├── JdbcRootPermition.java
    │   ├── SSHConnection.java
    │   └── UtilConfigure.java
    └── WebConfig.java

    12 directories, 44 files
    ```

- 회고
    - 프론트(Axios기반 비동기 통신)을 진행함에 있어 CORS 문제가 발생하여 `WebConfigurer`
    인터페이스를 상속받아 해결하였다.
    [CORS문제 발생_AJAX요청 시 발생 ,Model사용간에 불필요]
    - 프론트 개발시 API를 편리하게 확인할 수 있도록 API 문서(Swagger2API)를 활용하였지만 Controller에 부가적인 설정을 하지 않아 API문서 확인간에 의미 없는 문서가 됨
    [Swagger 문서화의 중요성]
    - 서버에 배포하기 위해서 기존 jar로 개발하던 환경을 war로 변경하기 위해 pom.xml의 세팅(tomcat-provided , war )빌드를 주기적으로 변경 및 local, docker , prod 환경마다 application.spring.profiles.active="" >> 변경을 필요.
    [환경간 빌드 또는 실행에 있어 주기적으로 세팅을 변경해야함]
    - application.yml에 부가적인 옵션을 기반으로 객체 생성후 Bean으로 등록,Bean 등록간에 @Getter,@Setter,@Component,@ConfigurationProperties(prefix="환경옵션명")애노테이션을 사용하여 Bean객체 등록및 생성이 가능하다.
    [환경마다 싱글톤 기반의 객체 생성이 필요하면 위와 같이 Bean을 등록용이]
    - java기반 시스템콜을 사용하기 위해 부가적인 라이브러리를 사용해야하며 , 현 프로젝트는 SSH접근하여 시스템콜을 하게 된다.
    [Java 기반 시스템콜 및 ssh 접근 가능]
    - 현 application은 StudentDothome라는 데이터 베이스를 기반으로 동작하고 있지만 Mysql 데이터베이스를 추가적으로 접근할 수 있었고 Mysql데이터 베이스에 접근하여 사용자를 생성 및 데이터 베이스 접근 권한을 부여,삭제하는 기능 추가 되어 있지만 JDBC로 표준 ORM인 Hibernate를 사용하지 않고 개발을 진행하였다.
    그로 인해 유지 보수 측면에서는 좋지 않은것 같다.
    또한 테스트 코드(쿼리가 동작하는 방식을 확인하기 위해 추가적인 라이브러리 필요 , 수정)이 필요하다

    ```xml
    <!-- https://mvnrepository.com/artifact/org.bgee.log4jdbc-log4j2/log4jdbc-log4j2-jdbc4 -->
    		<dependency>
    			<groupId>org.bgee.log4jdbc-log4j2</groupId>
    			<artifactId>log4jdbc-log4j2-jdbc4</artifactId>
    			<version>1.16</version>
    		</dependency>
    ```

    ```java
    		//Test Code
    		private final String url = "jdbc:log4jdbc:mysql://localhost:3306/mysql";
        private final String id = "root";
        private final String pwd = "password";
        private final String dummyUser = "host";
        private final String password = "123";
        private final String modifiyUser = "Host20121";
        Connection connection = null;
        Statement statement = null;

        @BeforeEach
        void jdbcConnection() throws Exception{
            Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
            connection = DriverManager.getConnection(url, id, pwd);
            statement = connection.createStatement();
            connection.setAutoCommit(false);
        }
    //실 동작 코드
    		private String url;
        private String id;
        private String pwd;

        @SneakyThrows({ClassNotFoundException.class})
        public JdbcRootPermition(UtilConfigure utilConfigure) {
            url = utilConfigure.getDatabaseUrl();
            id = utilConfigure.getDatabaseId();
            pwd = utilConfigure.getDatabasePwd();
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
    ```

    - 설계이후 개발 진행에 따라 테스트 코드 작성하지만 간단한 로직은 작성하지 않고 비교적 복잡한 비즈니스 로직 또는 DB와의 트랜잭션이 요구되는 로직은 작성해야함
    또한 초기 설계와 다르게 추가적으로 비즈니스 로직이 변경됨에 따라 테스트 코드가 깨지게 되고 급하다 보니 코드를 @Disabled를 하여 넘어가는 경우가 많았다.
    [초기 설계를 올바르게 진행하고 테스트 코드를 주기적을 확인하는 습관이 필요하다.]
    - Exception 처리가 불명확함 , Validation 또한 처리를 했지만 프론트에서 엄격하게 진행하거나 Template Engine을 사용하지 않아 크게 이득을 보지는 못했다.
    비즈니스 로직 작성간 많은 Exception을 발생하는데 가독성이 떨어지는짐
    [Exception처리 용이 변경 →타인의 Exception 코드를 분석하여 수정 요함 ]
    - Axios로 통신을 하다보니 GET HTTP method를 활용하여 진행할때 RequsetBody를 보내지 못하는 문제가 발생하였다.
    [GET method호출시 RequestBody보다는 RequestParam ,PathVariable를 사용]
    - Pagable를 활용하여 paging , sorting을 진행하였는데 사용자가 요구에 올바른 sorting이 진행되지 않는다.여러 옵션의 sorting condition을 추가하게 되면 발생하게 되며 사용자의 선택에 따른 동적 grouping by sorting 하는 쿼리 작성
    또한 동적으로 sort 조건을 받아 처리하는 부분 또한 타인의 코드를 분석하여 해결하긴 했지만 복잡하게 이루어져 있다.
    [query 작성에 의한 트레이드 오프 발생]
    - 기초적인 테스트 코드 작성은 하지만 중급 정도의 테스트 코드 작성은 하지 못함
    - Stream API를 활용하여 query의 결과값을 가공하는 부분에서 많은 시간 소모 또는 가독성이 좋지 않음 
    [StreamAPI 공부]
    - API구현후 controller테스트를 소스 코드로 진행한후 PostMan으로 마지막 테스트를 진행하였으며 PostMan 유틸리티 기능을 사용해보게 되었다.
    [소스 코드를 통한 Controller 테스트 ,  통합 테스트 진행 후 PostMan을 활용하여 실제 테스트 진행  ]

---

### 🔹 서버 구축 , 실제 배포 환경 구축 및 배포 자동화 회고

- 서버

    apache2 설정에 대한 기초적인 상식이 없어 설정하는 부분에서 많은 시간을 소모 하였다.

    그중 포트 포워딩 부분에서 많은 시간을 소모하였다.
    tomcat:8080 를 apache2 연동하여 80포트로 변경하려 했으나 많은 시간을 소모 하였고 진행하는 프로젝트가 SSR이 아닌 비동기 요청 기반 CSR로 진행하게 되어 /path/my/page 와 같이 /path로 시작하는 요청을 모두 /path 디렉토리의 index.html ,app.js가 처리하도록 구현하였다.
    tomcat은 API 제공 역할만을 하도록 구현하였다.

    기회가 된다면 tomcat과 apache2를 연동하는 부분을 수정하여 Spring의 Thymleaf를 사용하면 좋을거 같다.

- 실제 배포
프로젝트 시작전 PHP , MySQL이 동작하여 서비스를 진행하고 있어 기존 서비스진행되고 있는 환경에서 개발한것을 테스트 하는 부분에서  무리가 있어 로컬 환경의 Docker를 활용하여 기존 서비스와 동일한 환경을 구성하고 테스트한것을 테스트 통해 배포를 진행하였다.
테스트 함에 있어 배포전 많은 에러를 찾게 되어 용이한점들을 제공해주었다.

향후 변경점
기존 tomcat9을 로컬환경에서 동작 >> Docker 활용하여 무중단 배포 진행
    1.  Docker - tomcat이미지를 통해 API제공
    2.  Docker-tomcat을 통해 동작후 apache연동할 수 있다면 진행하기

- 배포 자동화

    간단한 쉘 스크립트로 작성 되어지만 아직까지 많은 불편함을 제공하고 있다.
    프론트 , 백엔드 코드를 빌드한후 SFTP를 통해 실제 서버에 전송하여 프론트 코드는 심볼릭 링크를 통해 조금 수월하지만 백엔드는 war빌드한 것을 /tomcat/webapps ROOT.war을 mv하며 tail -f를 통해 추가적인 로그들을 확인할 수 있는 스크립트 작성이 필요할것 같다.

    쉘 스크립트 작성하는 방법 또한 간단하게 배워야 할 것 같다.