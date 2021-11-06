package schoolserver.upload.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class StudentPortfolioLoginForm {


    @NotBlank(message = "공백입니다 다시 확인해주세요")
    private String name;
    @Max(value = 9,message = "학번은 최대 9 자리 입니다")
    private Integer studentCode;
    private String password;
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    private String phoneNumber;


}
