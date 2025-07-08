package pack.config;
// 날짜와 시간의 형식을 전역적으로 설정

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 날짜와 시간에 대한 형식설정을 관리  "yyyy-MM-dd'T'HH:mm:ss"
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();

        registrar.setDateFormatter(DateTimeFormatter.ISO_LOCAL_DATE); // 로컬 날짜와 시간을 ISO 표준에 맞춤
        registrar.registerFormatters(registry);
    }
}
