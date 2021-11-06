package schoolserver.upload.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentPortfolioRepositoryTest {

    @Autowired
    private StudentPortfolioRepository studentPortfolioRepository;

    @Test
    @DisplayName("")
    void StudentPortfolioRepositoryTest() throws Exception{
        // given
        studentPortfolioRepository.findByNameAndStudentCode("김유민", 201610309);
        // when

        // then
    }

}