package kr.ac.seowon.media.studentadminsite.controller;

import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hello")
@Transactional
public class HelloController {

    private final StudentRepository studentRepository;

    @GetMapping
    public String hello(Model model) throws SQLException {
        Student student = Student.createStudent("안녕", 2016103+ LocalDateTime.now().getSecond());
        studentRepository.savec(student).orElseThrow(() -> new SQLIntegrityConstraintViolationException("유니크키 오류"));
        model.addAttribute("name", "Hello Spring & thymeleaf");
        return "hello";
    }

    @GetMapping("/db")
    public String hellodb(Model model) {
        List<Student> all = studentRepository.findAll();
        if (all.size() == 0) {
            throw new IllegalStateException("학생이 존재하지 않습니다");
        }
        Student student = studentRepository.findById(1).orElseThrow(() -> new IllegalStateException("Not Found Student"));
        model.addAttribute("student", student);
        model.addAttribute("students", all);
        return "db";
    }

    @ExceptionHandler({IllegalStateException.class,SQLIntegrityConstraintViolationException.class})
    public String exceptionModel(Model model , Exception e){
        model.addAttribute("exception", e.getMessage());
        return "exception";
    }
}
