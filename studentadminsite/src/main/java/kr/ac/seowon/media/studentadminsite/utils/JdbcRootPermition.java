package kr.ac.seowon.media.studentadminsite.utils;

import kr.ac.seowon.media.studentadminsite.exception.UtilJdbcConnectionException;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRootPermition {

    private String url;
    private String id;
    private String pwd;
    Connection connection = null;

    @SneakyThrows({ClassNotFoundException.class, SQLException.class})
    public JdbcRootPermition(UtilConfigure utilConfigure) {
        url = utilConfigure.getDatabaseUrl();
        id = utilConfigure.getDatabaseId();
        pwd = utilConfigure.getDatabasePwd();
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
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new UtilJdbcConnectionException("데이터 베이스 사용자 삭제 에러가 발생하였습니다.", e.getSQLState(), e.getErrorCode());
        }

    }

    @SneakyThrows(UtilJdbcConnectionException.class)
    public void createJDBCMysqlUser(String databaseName) {
        try (Connection connection = DriverManager.getConnection(url, id, pwd)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute("create user " + databaseName + "@'%' identified by '1234'");
                statement.execute("create database " + databaseName);
                statement.execute("grant all privileges on " + databaseName + ".* to " + databaseName + "@'%';");
                statement.execute("FLUSH privileges");
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new UtilJdbcConnectionException("데이터 베이스 사용자 생성 오류 발생 실패 했습니다.", e.getSQLState(), e.getErrorCode());
        }
    }



}
