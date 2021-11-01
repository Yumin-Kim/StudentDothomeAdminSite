package kr.ac.seowon.media.studentadminsite.utils;

import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcRootPermitionTest {

    private final String url = "jdbc:log4jdbc:mysql://localhost:3306/mysql";
    private final String id = "root";
    private final String pwd = "password";
    private final String dummyUser = "s20202020";
    private final String password = "123";
    private final String modifiyUser = "Host20";
    Connection connection = null;
    Statement statement = null;


    @BeforeEach
    void jdbcConnection() throws Exception {
        Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        //Root
        connection = DriverManager.getConnection(url, id, pwd);
        statement = connection.createStatement();
        connection.setAutoCommit(false);
    }

    @Test
    @Order(2)
    void jdbcTest() throws Exception {
        statement.execute("drop user " + dummyUser + "@'%'");
        statement.execute("drop database " + dummyUser);
        statement.execute("FLUSH privileges");
        connection.commit();
    }

    @Test
    @DisplayName("mysql 사용자 생성 테스트 코드")
    @Order(1)
    void createMysqlUserJDBCConnection() throws Exception {
        statement.execute("create user " + dummyUser + "@'%' identified by '" + password + "'");
        statement.execute("create database " + dummyUser);
        statement.execute("grant all privileges on " + dummyUser + ".* to " + dummyUser + "@'%';");
        statement.execute("FLUSH privileges");
        connection.commit();
    }

    @Test
    @DisplayName("현 사용자 확인 쿼리")
    void dumpAndCreateMysqlUser() throws Exception {
        ResultSet resultSet = statement.executeQuery("select user from user where user like \"s%\"");
        List<MysqlUserBean> arrayList = new ArrayList<MysqlUserBean>();
        while (resultSet.next()) {
            final MysqlUserBean mysqlUserBean = new MysqlUserBean();
            final String username = resultSet.getString("user");
            mysqlUserBean.setUser(username);
            arrayList.add(mysqlUserBean);
        }
        arrayList.stream()
                .forEach(data -> System.out.println("data = " + data.getUser()));
    }

    @Test
    @DisplayName("사용자 생성 확인 삭제 통합 코드")
    void integrationJDBCTest() throws Exception {
        // given
        String testUser = "javatestcode";
        statement.execute("create user " + testUser + "@'%' identified by '" + password + "'");
        statement.execute("create database " + testUser);
        statement.execute("grant all privileges on " + testUser + ".* to " + testUser + "@'%';");
        statement.execute("FLUSH privileges");
        ResultSet resultSet = statement.executeQuery("select user from user where user like \"s%\"");
        List<MysqlUserBean> arrayList = new ArrayList<MysqlUserBean>();
        while (resultSet.next()) {
            final MysqlUserBean mysqlUserBean = new MysqlUserBean();
            final String username = resultSet.getString("user");
            mysqlUserBean.setUser(username);
            arrayList.add(mysqlUserBean);
        }
        arrayList.stream()
                .forEach(data -> System.out.println("data = " + data.getUser()));
        final List<MysqlUserBean> collect = arrayList.stream()
                .filter(data -> data.equals(testUser))
                .collect(Collectors.toList());
        statement.execute("drop user " + testUser + "@'%'");
        statement.execute("drop database " + testUser);
        statement.execute("FLUSH privileges");
        connection.commit();
        final Statement statement1 = connection.createStatement();
        // then
        ResultSet resultSet1 = statement1.executeQuery("select user from user where user like \"s%\"");
        List<MysqlUserBean> arrayList1 = new ArrayList<MysqlUserBean>();
        while (resultSet1.next()) {
            final MysqlUserBean mysqlUserBean = new MysqlUserBean();
            final String username = resultSet1.getString("user");
            mysqlUserBean.setUser(username);
            arrayList1.add(mysqlUserBean);
        }
        arrayList1.stream()
                .forEach(data -> System.out.println("data = " + data.getUser()));
    }


    @Test
    @DisplayName("사용자 일괄 저장후 일괄 삭제 테스트")
    void JdbcRootPermitionTest() throws Exception {
        // given/**/
        String dummyUser1 = "testaa";
        String dummyUser2 = "testbb";
        statement.execute("create user " + dummyUser1 + "@'%' identified by '" + password + "'");
        statement.execute("create database " + dummyUser1);
        statement.execute("grant all privileges on " + dummyUser1 + ".* to " + dummyUser1 + "@'%';");
        statement.execute("FLUSH privileges");
        statement.execute("create user " + dummyUser2 + "@'%' identified by '" + password + "'");
        statement.execute("create database " + dummyUser2);
        statement.execute("grant all privileges on " + dummyUser2 + ".* to " + dummyUser2 + "@'%';");
        statement.execute("FLUSH privileges");

        List.of(dummyUser1, dummyUser2).stream()
                .forEach(data -> {
                    try {
                        extracted(data);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void extracted(String dummyUser) throws SQLException {
        statement.execute("drop user " + dummyUser + "@'%'");
        statement.execute("drop database " + dummyUser);
        statement.execute("FLUSH privileges");
        connection.commit();
    }

    @Getter
    @Setter
    public static class MysqlUserBean {
        private String user;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

}