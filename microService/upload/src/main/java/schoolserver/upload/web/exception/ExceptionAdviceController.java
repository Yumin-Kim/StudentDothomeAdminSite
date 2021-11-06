package schoolserver.upload.web.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler(StudentException.class)
    public String StudentException(Exception e , Model model){
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
