package schoolserver.upload.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import schoolserver.upload.web.dto.DefaultStudentInfo;
import schoolserver.upload.web.dto.PortFolioForm;
import schoolserver.upload.web.exception.ErrorCode;
import schoolserver.upload.web.exception.StudentException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
    private final StudentPortfolioRepository studentPortfolioRepository;
    private final PasswordEncoder passwordEncoder;

    //학생 회원 가입 >> 실질적으로 정보 수정
    @Transactional
    public void createMember(StudentPortfolioLoginForm loginForm) {
        final StudentPortfolio studentPortfolio = studentPortfolioRepository.findByNameAndStudentCode(loginForm.getName(), loginForm.getStudentCode())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        if (studentPortfolio.getEmail() != null) {
//            new StudentPortfolioLoginForm(studentPortfolio,passwordEncoder.);
//            throw new StudentException(ErrorCode.STUDENT_FIND_INFO,studentPortfolio);
        }
        studentPortfolio.registerStudent(passwordEncoder.encode(loginForm.getPassword()), loginForm.getEmail(), loginForm.getPhoneNumber());
    }


    //로그인
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String studentCode) throws UsernameNotFoundException {
        final StudentPortfolio studentPortfolio = studentPortfolioRepository.findByStudentCode(Integer.parseInt(studentCode))
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        return new UserLoginSuccessDto(studentPortfolio);
    }

    @Transactional
    public Boolean registerPortfolio(String insertImageFormatData, PortFolioForm portFolioForm, DefaultStudentInfo studentInfo) {
        final StudentPortfolio studentPortfolio = studentPortfolioRepository.findByNameAndStudentCode(studentInfo.getName(), studentInfo.getStudentCode())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        studentPortfolio.updatePortfolio(
                portFolioForm.getYoutubeLink(),
                portFolioForm.getSpeechLink(),
                portFolioForm.getDescription(),
                insertImageFormatData,
                portFolioForm.getJob(),
                portFolioForm.getMainDescription());
        return true;
    }
}
