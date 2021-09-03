package kr.ac.seowon.media.studentadminsite;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
//WebMockMvc에서 @EnableJpaAuditing을 사용하지 못해 별도로 분리했다
public class JpaAuditingConfiguraion {


}
