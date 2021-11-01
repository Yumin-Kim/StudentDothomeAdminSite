package kr.ac.seowon.media.studentadminsite.utils.was;

import kr.ac.seowon.media.studentadminsite.dto.was.UserDatabaseErrorLog;
import kr.ac.seowon.media.studentadminsite.exception.ErrorCode;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.exception.utilexception.UtilJdbcConnectionException;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * Bean으로 등록하면 좋을지??
 * 테이블 존재 여부 확인만 하고 등록 할때 마다 insert 쿼리 동작
 * spring-boot 에서는 insert 쿼리 , 테이블 확인 쿼리만 동작
 * * insert 쿼리 동작시 not null값을 한 값은 필히 저장
 * 쉘 스크립트에서는 성공 실패 여부에 따라 update쿼리 동작
 * 실패 여부시 로그 파일의 글을 저장할것인데 파일을 따로 저장하는 방식이 괜찮은지 비교해보기
 */
@Component
@RequiredArgsConstructor
public class UserDataBaseJDBC {

    private final UtilConfigure utilConfigure;
    private Connection connection = null;
    private Statement statement = null;

    /**
     * 테스트 진행간 사용자 데이터 베이스의 에러 로그 테이블 생성 및 insert 쿼리 동작
     * @param userConnectDto
     * @return
     */
    @SneakyThrows(UtilJdbcConnectionException.class)
    public UserDatabaseErrorLog.UserWasErrorLogResultDto registerErrorLogTable(UserDatabaseErrorLog.UserConnectDto userConnectDto) {
        UserDatabaseErrorLog.UserWasErrorLogResultDto result = null;
        getUserDatabaseConnection(userConnectDto);
        // given
        try {
            statement.execute("select * from was_error_logs");
            result = insertDataQuery(userConnectDto.getWasInfoId());
        } catch (SQLException tableException) {
            try {
                statement.execute("CREATE TABLE was_error_logs (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `createdAt` DATETIME(6) NOT NULL,\n" +
                        "  `last_modified` DATETIME(6) NULL,\n" +
                        "  `deleted` DATETIME(6) NULL,\n" +
                        "  `is_delete` BIT(1) NOT NULL,\n" +
                        "  `error_logs` LONGTEXT NULL,\n" +
                        "  `was_info_id` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`id`));\n");
                result = insertDataQuery(userConnectDto.getWasInfoId());
            } catch (SQLException createException) {
                try {
                    connection.rollback();
                    statement.close();
                } catch (SQLException e) {
                    throw new UtilJdbcConnectionException(ErrorCode.JDBC_COMMIT_ROLLBACK, e.getMessage(), e.getErrorCode());
                }
            }
        }
        try {
            connection.commit();
            statement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new UtilJdbcConnectionException(ErrorCode.JDBC_COMMIT_ROLLBACK, ex.getMessage(), ex.getErrorCode());
            }
        }
        return result;
    }

    public UserDatabaseErrorLog deleteErrorLogTable() {
        return null;
    }


    @SneakyThrows(UtilJdbcConnectionException.class)
    private void getUserDatabaseConnection(UserDatabaseErrorLog.UserConnectDto userConnectDto) {
        try {
            connection = DriverManager.getConnection(userConnectDto.getUserDatabaseUrl(), utilConfigure.getDatabaseId(), utilConfigure.getDatabasePwd());
            statement = connection.createStatement();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new UtilJdbcConnectionException(ErrorCode.JDBC_CONNECTION_ERROR, e.getMessage(), e.getErrorCode());
        }
    }

    @SneakyThrows(UtilJdbcConnectionException.class)
    private UserDatabaseErrorLog.UserWasErrorLogResultDto insertDataQuery(Integer wasInfoId) {
        try {
            UserDatabaseErrorLog.UserWasErrorLogResultDto userWasErrorLogResultDto = null;
            final PreparedStatement preparedStatement = connection.prepareStatement("insert into was_error_logs(createdAt,is_delete,was_info_id) values(NOW(),?,?)");
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, wasInfoId);
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    final ResultSet resultSet = preparedStatement.executeQuery("select * from was_error_logs where id = " + generatedKeys.getInt(1) + "");
                    if (resultSet.next()) {
                        userWasErrorLogResultDto = new UserDatabaseErrorLog.UserWasErrorLogResultDto();
                        userWasErrorLogResultDto.setId(resultSet.getInt("id"));
                        userWasErrorLogResultDto.setIsDelete(resultSet.getBoolean("is_delete"));
                        userWasErrorLogResultDto.setCreatedAt(resultSet.getTimestamp("createdAt").toLocalDateTime());
                    }
                } else {
                    throw new StudentException(ErrorCode.JDBC_USER_DATABASE_INSERT_ERROR);
                }
            }
            return userWasErrorLogResultDto;
        } catch (SQLException e) {
            throw new UtilJdbcConnectionException(ErrorCode.JDBC_USER_DATABASE_INSERT_ERROR, e.getMessage(), e.getErrorCode());
        }
    }

}
