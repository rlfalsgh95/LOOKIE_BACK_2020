package kr.or.connect.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"kr.or.connect.service", "kr.or.connect.dao"})
@Import({DBConfig.class})

public class ApplicationConfig {
}
