package kr.ac.seowon.media.studentadminsite;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  Spring-boot web관련 설정
 *  현재는 CORS 설정만 되어 있다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5050","http://localhost:80", "http://localhost:5000", "http://localhost:8080","http://localhost","http://media.seowon.ac.kr")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
