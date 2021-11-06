package schoolserver.upload.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import schoolserver.upload.web.dto.DefaultStudentInfo;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberQueryService {

    private final StudentPortfolioRepository studentPortfolioRepository;

    //정보 조회
    public DefaultStudentInfo getStudentInfo(String name){
        final StudentPortfolio findStudent = studentPortfolioRepository.findByName(name);
        return new DefaultStudentInfo(findStudent);
    }


}
