package schoolserver.upload.web;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserLoginSuccessDto  extends User {

    public UserLoginSuccessDto(StudentPortfolio studentPortfolio) {
        super(studentPortfolio.getName(), studentPortfolio.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }

}
