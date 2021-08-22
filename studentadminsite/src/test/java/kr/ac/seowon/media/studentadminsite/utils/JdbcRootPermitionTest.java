package kr.ac.seowon.media.studentadminsite.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class JdbcRootPermitionTest {

    private final String url = "jdbc:log4jdbc:mysql://localhost:3306/mysql";
    private final String id = "root";
    private final String pwd = "wjqrmsrma!wl6311";
    private final String dummyUser = "Host";
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


    @Test
    void jdbcTest() throws Exception {
        statement.execute("drop user "+dummyUser+"@'%'");
        statement.execute("drop database "+dummyUser);
        statement.execute("FLUSH privileges");
        connection.commit();
    }

    @Test
    @DisplayName("mysql 사용자 생성 테스트 코드")
    void createMysqlUserJDBCConnection() throws Exception{
        statement.execute("create user "+dummyUser+"@'%' identified by '1234'");
        statement.execute("create database " + dummyUser);
        statement.execute("grant all privileges on " + dummyUser + ".* to " + dummyUser + "@'%';");
        statement.execute("FLUSH privileges");
//        statement.execute("create table "+dummyUser+".member (id int auto_increment , username varchar(255) not null , password varchar(255) not null , primary key(id));");
//        statement.executeUpdate("insert into "+dummyUser+".member(username , password ) values ('username1' , 'password' )");
        connection.commit();
    }

    @Test
    @DisplayName("사용자 데이터 베이스 변경 로직 >> shell 작성 요구")
    void dumpAndCreateMysqlUser() throws Exception{
//        statement.execute("")
    }

}