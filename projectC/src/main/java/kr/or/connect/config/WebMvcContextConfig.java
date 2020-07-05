package kr.or.connect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//  Spring MVC와 Swagger2의 기본 설정이 자동으로 설정 된다.
@EnableWebMvc   // Spring MVC설정
@EnableSwagger2 // Swagger2설정
@ComponentScan(basePackages = {"kr.or.connect.controller"})
public class WebMvcContextConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final int cachePeriod = 31556926;

        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:META-INF/resourses/webjars").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(cachePeriod);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(cachePeriod);
    }

/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
    }*/

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    // DefaultServlet에 대한 설정을 합니다.
    // DispatcherServlet이 처리하지 못하는 URL은 DefaultServlet이 처리하게 됩니다.
    // 해당 설정이 없으면 자동 생성된 Swaager 페이지를 볼 수 없다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /*
        Swagger2를 사용하려면 Docket객체를 Bean으로 설정해야 한다.
        Docker객체에는 어떤 경로의 Web API들을 자동으로 문서화 할 것인지에 대한 설정과 문서 설명에 대한 내용이 포함된다.
        Swagger 사용 시에는 Docket Bean 을 품고있는 설정 클래스 1개가 기본으로 필요하다.
        Spring Boot 에서는 이 기본적인 설정파일 1개로 Swagger 와 Swagger UI 를 함께 사용가능하지만,
        Spring MVC 의 경우 Swagger UI 를 위한 별도의 설정이 필요하다.
        이는, Swagger UI 를 ResourceHandler 에 수동으로 등록해야 하는 작업인데,
        Spring Boot 에서는 이를 자동으로 설정해주지만 Spring MVC 에서는 그렇지 않기 때문이다.
    */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.ant("/api/**"))// PathSelectors.any() 를 할경우 모든 경로가 다 사용된다. RestController가 아닌 것 까지 사용된다.
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

     // API Info
    private ApiInfo apiInfo() {
        Contact contact = new Contact("길민호", "https://www.edwith.org", "rlfalsgh95@naver.com");
        ApiInfo apiInfo =  new ApiInfo("boostcourse ProjectC", "APIs Sample", "Sample Doc 0.1v", "", contact, "This sentence will be display.", "/");
        return apiInfo;
    }
}