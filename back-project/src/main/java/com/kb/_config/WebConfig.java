package com.kb._config;

<<<<<<< Updated upstream
import lombok.Getter;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
>>>>>>> Stashed changes
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

@Slf4j
@Configuration
@PropertySource({"classpath:/application.properties"})
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer implements WebMvcConfigurer {
//    @Value("${os_type} == win ? ${file_save_location_win} : ${file_save_location_other}")
    @Value("#{'${os_type}' == 'win' ? '${file_save_location_win}':'${file_save_location_other}'}")
    public String LOCATION;

    final long MAX_FILE_SIZE = 1024 * 1024 * 10L;
    final long MAX_REQUEST_SIZE =  1024 * 1024 * 20L;
    final int FILE_SIZE_THRESHOLD = 1024 * 1024 * 5;;

//    @Value("${upload.maxFileSize}") Long maxFileSize;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class, SecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    // POST body 문자 인코딩 필터 설정 - UTF-8 설정
//    protected Filter[] getServletFilters() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//
//        return new Filter[] {characterEncodingFilter};
//    }


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        log.info("Max File Size: " + maxFileSize);

        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        MultipartConfigElement multipartConfig =
                new MultipartConfigElement(
                        LOCATION,   // 업로드 처리 디렉토리 경로
                        MAX_FILE_SIZE,	// 업로드 가능한 파일 하나의 최대 크기
                        MAX_REQUEST_SIZE,	// 업로드 가능한 전체 최대 크기(여러 파일 업로드 하는 경우)
                        FILE_SIZE_THRESHOLD		// 메모리 파일의 최대 크기(이보다 작으면 실제 메모리에서만 작업)
                );
        registration.setMultipartConfig(multipartConfig);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080") // Vue 앱의 URL. 배포 시에 production URL로 수정
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

<<<<<<< Updated upstream
=======
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    // ObjectMapper 빈 추가
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule()); // LocalDate 지원
    }
>>>>>>> Stashed changes
}
