package kr.ac.seowon.media.studentadminsite;

import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class StudentadminsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentadminsiteApplication.class, args);
	}


}
