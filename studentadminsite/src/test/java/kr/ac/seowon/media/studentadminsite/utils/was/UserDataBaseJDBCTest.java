package kr.ac.seowon.media.studentadminsite.utils.was;

import kr.ac.seowon.media.studentadminsite.dto.was.UserDatabaseErrorLog;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDataBaseJDBCTest {

    private Connection userDatabaseConnection = null;
    private Statement userDatabaseStatement = null;
    private String userDatabase = "s202110309";
    private String userUrl = "jdbc:log4jdbc:mysql://localhost:3306/" + userDatabase;
    private String userId = "root";
    private String userPwd = "password";

    private void getConnection() throws SQLException {
        userDatabaseConnection = DriverManager.getConnection(userUrl, userId, userPwd);
        userDatabaseStatement = userDatabaseConnection.createStatement();
        userDatabaseConnection.setAutoCommit(false);
    }

    private void insertDataQuery(Integer wasInfoId) throws SQLException {
        final PreparedStatement preparedStatement = userDatabaseConnection.prepareStatement("insert into was_error_logs(createdAt,is_delete,was_info_id) values(NOW(),?,?)",Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setBoolean(1, false);
        preparedStatement.setInt(2, wasInfoId);
        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                final ResultSet resultSet = userDatabaseStatement.executeQuery("select * from was_error_logs where id = " + generatedKeys.getInt(1) + "");
                if (resultSet.next()) {
                    final UserDatabaseErrorLog.UserWasErrorLogResultDto userWasErrorLogResultDto = new UserDatabaseErrorLog.UserWasErrorLogResultDto();
                    userWasErrorLogResultDto.setId(resultSet.getInt("id"));
//                    userWasErrorLogResultDto.setErrorLogs(resultSet.getString("error_logs"));
                    userWasErrorLogResultDto.setIsDelete(resultSet.getBoolean("is_delete"));
                    userWasErrorLogResultDto.setCreatedAt(resultSet.getTimestamp("createdAt").toLocalDateTime());
//                    userWasErrorLogResultDto.setDeleted(resultSet.getTimestamp("deleted").toLocalDateTime());
//                    userWasErrorLogResultDto.setLastModified(resultSet.getTimestamp("last_modified").toLocalDateTime());
                    System.out.println("useWasErr = " + userWasErrorLogResultDto.toString());
                }
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    /**
     * ????????? ?????? ?????? ????????? ?????? ?????? ?????? ?????? insert ?????? ??????
     * spring-boot ????????? insert ?????? , ????????? ?????? ????????? ??????
     * * insert ?????? ????????? not null?????? ??? ?????? ?????? ??????
     * ??? ????????????????????? ?????? ?????? ????????? ?????? update?????? ??????
     * ?????? ????????? ?????? ????????? ?????? ?????????????????? ????????? ?????? ???????????? ????????? ???????????? ???????????????
     *
     * @throws Exception
     */
    @Test
    @DisplayName("????????? waserrorlogs ????????? ?????? ?????? ?????? ??? ?????? ????????? was ?????? ?????? ??????(?????? ?????? , was id)")
    public void registerErrorLogTable() throws SQLException {
        getConnection();
        // given
        try {
            userDatabaseStatement.execute("select * from was_error_logs");
            insertDataQuery(1);
        } catch (SQLException tableException) {
            try {
                userDatabaseStatement.execute("CREATE TABLE was_error_logs (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `createdAt` DATETIME(6) NOT NULL,\n" +
                        "  `last_modified` DATETIME(6) NULL,\n" +
                        "  `deleted` DATETIME(6) NULL,\n" +
                        "  `is_delete` BIT(1) NOT NULL,\n" +
                        "  `error_logs` LONGTEXT NULL,\n" +
                        "  `was_info_id` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`id`));\n");
                insertDataQuery(1);
            } catch (SQLException createException) {
                throw new StudentException(ErrorCode.JDBC_CONNECTION_ERROR);
            }
        }
        userDatabaseConnection.commit();
        userDatabaseStatement.close();
        // when

        // then
    }

    @Test
    void deleteErrorLogTable() {
    }

    @Test
    @DisplayName("")
    void shellScript_queryTest() throws Exception{
        // given

        // when

        // then
    }

}