package kr.ac.seowon.media.studentadminsite.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Builder
public class Res<T> {

    private String message;
    private String httpStatusCode;
    private T data;

    public static <T> Res<T> isOkWithData(T data, String message) {
        return (Res<T>) Res.builder()
                .data(data)
                .httpStatusCode(HttpStatus.OK.toString())
                .message(message)
                .build();
    }

    public static <T> Res<T> isOkByMessage(String message) {
        return (Res<T>) Res.builder()
                .message(message)
                .httpStatusCode(HttpStatus.OK.toString())
                .build();
    }

    public static <T> Res<T> isErrorWthData(T data, String message,String httpStatusCode) {
        return (Res<T>) Res.builder()
                .data(data)
                .httpStatusCode(httpStatusCode)
                .message(message)
                .build();
    }

    public static <T> Res<T> isErrorByMessage(String message, String httpStatusCode) {
        return (Res<T>) Res.builder()
                .message(message)
                .httpStatusCode(httpStatusCode)
                .build();
    }

}
