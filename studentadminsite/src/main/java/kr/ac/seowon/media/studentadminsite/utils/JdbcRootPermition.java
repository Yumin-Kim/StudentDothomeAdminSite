package kr.ac.seowon.media.studentadminsite.utils;

import kr.ac.seowon.media.studentadminsite.exception.UtilJdbcConnectionException;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRootPermition {

    //local mode
    private final String url = "jdbc:mysql://localhost:3306/mysql";
    private final String id = "root";
    private final String pwd = "wjqrmsrma!wl6311";
    //docker mode
//    private final String url = "jdbc:mysql://localhost:3306/mysql";
//    private final String id = "root";
//    private final String pwd = "wjqrmsrma!wl6311";

    //multi mode
//    private final String url = "jdbc:mysql://localhost:3306/mysql";
//    private final String id = "root";
//    private final String pwd = "wjqrmsrma!wl6311";

    Connection connection = null;

    @SneakyThrows({ClassNotFoundException.class, SQLException.class})
    public JdbcRootPermition() {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, id, pwd);
    }

    @SneakyThrows(UtilJdbcConnectionException.class)
    public void deleteDatabase(String databaseName) {
        try (Connection connection = DriverManager.getConnection(url, id, pwd)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute("drop user " + databaseName + "@'%'");
                statement.execute("drop database " + databaseName);
                statement.execute("FLUSH privileges");
            } catch (SQLException e) {
                connection.rollback();
                throw new UtilJdbcConnectionException("데이터 베이스 사용자 삭제 에러가 발생하였습니다.", e.getSQLState(), e.getErrorCode());
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new UtilJdbcConnectionException("데이터 베이스 접근 실패 했습니다.", e.getSQLState(), e.getErrorCode());
        }

    }

    @SneakyThrows(UtilJdbcConnectionException.class)
    public void createJDBCMysqlUser(String databaseName) {
        try (
                Connection connection = DriverManager.getConnection(url, id, pwd)
             Statement statement=connection.createStatement() {
        }
        ) {
            connection.setAutoCommit(false);
            try () {
                statement.execute("create user " + databaseName + "@'%' identified by '1234'");
                statement.execute("create database " + databaseName);
                statement.execute("grant all privileges on " + databaseName + ".* to " + databaseName + "@'%';");
                statement.execute("FLUSH privileges");
                statement.execute("create table " + databaseName + ".member (id int auto_increment , username varchar(255) not null , password varchar(255) not null , primary key(id));");
                statement.executeUpdate("insert into " + databaseName + ".member(username , password ) values ('username1' , 'password' )");
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new UtilJdbcConnectionException("데이터 베이스 사용자 생성 에러가 발생하였습니다.", e.getSQLState(), e.getErrorCode());
            }
            connection.commit();
        } catch (SQLException e) {
            throw new UtilJdbcConnectionException("데이터 베이스 접근 실패 했습니다.", e.getSQLState(), e.getErrorCode());
        }
    }


}
