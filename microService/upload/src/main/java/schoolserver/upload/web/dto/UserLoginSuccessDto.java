package schoolserver.upload.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import schoolserver.upload.web.StudentPortfolio;

import java.util.List;

@Getter
@Setter
public class UserLoginSuccessDto extends User {

    public UserLoginSuccessDto(StudentPortfolio studentPortfolio) {
        super(studentPortfolio.getName(), studentPortfolio.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
