package kr.ac.seowon.media.studentadminsite.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AdminObserveReq {
    @Getter
    @Setter
    public static class BasicStudentDto {
        @NotBlank(message = "name이 존재하지 않습니다.")
        private String name;
        @Min(value = 200000000, message = "해당 학번 입력이 잘못되었습니다.")
        @Max(value = 300000000,message ="해당 학번 입력이 잘못되었습니다." )
        @NotBlank(message = "studentCode를 입력하지 않았습니다.")
        private Integer studentCode;
    }
}
