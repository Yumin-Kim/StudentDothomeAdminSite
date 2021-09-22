package kr.ac.seowon.media.studentadminsite.utils;

import kr.ac.seowon.media.studentadminsite.exception.utilexception.UtilJdbcConnectionException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JdbcRootPermition {

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

    @SneakyThrows(UtilJdbcConnectionException.class)
    public List<MysqlUserBean> selectDatabase() {
        try (Connection connection = DriverManager.getConnection(url, id, pwd)) {
            connection.setAutoCommit(false);
            try (final Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select user from user where user like \"s%\"");
                List<MysqlUserBean> mysqlBeanLists = new ArrayList<MysqlUserBean>();
                while (resultSet.next()) {
                    final MysqlUserBean mysqlUserBean = new MysqlUserBean();
                    final String username = resultSet.getString("user");
                    mysqlUserBean.setUser(username);
                    mysqlBeanLists.add(mysqlUserBean);
                }
                return mysqlBeanLists;
            } catch (SQLException e) {
                connection.rollback();
            }
            connection.commit();
        } catch (SQLException e) {
            throw new UtilJdbcConnectionException("데이터 베이스 사용자 조회 에러가 발생하였습니다.", e.getSQLState(), e.getErrorCode());
        }
        return null;
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
    public void createJDBCMysqlUser(String databaseName,String password) {
        log.info("databaseName = {} , password = {}",databaseName,password);
        try (Connection connection = DriverManager.getConnection(url, id, pwd)) {
            log.info("DriverManager.getConnection");
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                log.info("connection.createStatement");
                statement.execute("create user "+databaseName+"@'%' identified by '"+password+"'");
                statement.execute("create database " + databaseName);
                statement.execute("grant all privileges on " + databaseName + ".* to " + databaseName + "@'%';");
                statement.execute("FLUSH privileges");
            } catch (SQLException e) {
                connection.rollback();
            }
            connection.commit();
        } catch (SQLException e) {
            log.error("SQLException = {}",e.getMessage());
            throw new UtilJdbcConnectionException("데이터 베이스 사용자 생성 오류 발생 실패 했습니다.", e.getSQLState(), e.getErrorCode());
        }
    }



}
