package schoolserver.upload.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import schoolserver.upload.web.MemberQueryService;
import schoolserver.upload.web.MemberService;
import schoolserver.upload.web.StudentPortfolioLoginForm;
import schoolserver.upload.web.UserLoginSuccessDto;
import schoolserver.upload.web.dto.DefaultStudentInfo;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @GetMapping
    public String getBasicPage(@AuthenticationPrincipal UserLoginSuccessDto userLoginSuccessDto , Model model) {
        if (userLoginSuccessDto != null) {
            log.info(userLoginSuccessDto.getUsername());
            model.addAttribute("user", userLoginSuccessDto.getUsername());
            DefaultStudentInfo studentInfo = memberQueryService.getStudentInfo(userLoginSuccessDto.getUsername());
            log.info("{}",studentInfo.toString());
            model.addAttribute("info", studentInfo);
        }
        return "index";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "loginForm";
    }

    //TODO Validation 추가
    @PostMapping("/signup")
    public String createMember(@ModelAttribute StudentPortfolioLoginForm loginForm){
        log.info("{}",loginForm.toString());
        memberService.createMember(loginForm);
        return "redirect:/";
    }
}
