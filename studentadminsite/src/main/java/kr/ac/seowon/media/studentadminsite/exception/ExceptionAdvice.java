package kr.ac.seowon.media.studentadminsite.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.dto.Res;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Res validationException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, List<String>> resultValidatoinData = bindingResult.getFieldErrors().stream()
                    .collect(
                            groupingBy(f -> f.getField(),
                                    mapping(fieldError -> fieldError.getDefaultMessage()
                                            , toList())));
            return Res.isErrorWthData(resultValidatoinData, "입력한 정보를 다시 확인해주세요", HttpStatus.BAD_REQUEST.toString());
        } else {
            return null;
        }
    }

    //TODO Exception 코드 재수정하여 Return
    @ExceptionHandler({UtilJdbcConnectionException.class})
    public Res jdbcException(SQLException e) {

        return Res.isErrorByMessage(e.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler({SSHException.class})
    public Res sshConnetionException(Exception error) {
        return Res.isErrorByMessage(error.getMessage(), HttpStatus.REQUEST_TIMEOUT.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({StudentException.class, AdminException.class,AdminObserveException.class,StudentSiteInfoException.class})
    public Res dataNotFoundException(Exception error) {
        return Res.isErrorByMessage(error.getMessage(), HttpStatus.BAD_REQUEST.name());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InsertDuplicateException.class})
    public Res studentDuplicateInsertExpception(Exception e) throws JsonProcessingException {
        String message = e.getMessage();
        ObjectMapper objectMapper = new ObjectMapper();
        List errorList = objectMapper.readValue(message, List.class);
        return Res.isErrorWthData(errorList, "입력한 학생이 중복되었습니다.", HttpStatus.BAD_REQUEST.name());
    }


    //TODO ReponseEntity를 통해서 리턴
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({StudnetException.class,AdminException.class})
//    public ResponseEntity<Res> dataNotFoundException(Exception error) {
//        Res<Object> errorByMessage = Res.isErrorByMessage(error.getMessage(), HttpStatus.BAD_REQUEST.name());
//        return new ResponseEntity<>(errorByMessage, HttpStatus.BAD_REQUEST);
//    }
}
