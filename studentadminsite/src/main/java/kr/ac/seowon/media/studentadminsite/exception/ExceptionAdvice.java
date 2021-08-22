package kr.ac.seowon.media.studentadminsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.annotation.HttpMethodConstraint;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({BindException.class})
    public Object validationException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, List<String>> collect = bindingResult.getFieldErrors().stream()
                    .collect(
                            groupingBy(f -> f.getField(),
                                    mapping(fieldError -> fieldError.getDefaultMessage()
                                            , toList())));
            return collect;
        } else {
            return null;
        }
    }

    //TODO Exception 코드 재수정하여 Return
    @ExceptionHandler({UtilJdbcConnectionException.class,StudnetException.class,SSHException.class})
    public String jdbcException(Exception e) {
        return e.getMessage();
    }
}
