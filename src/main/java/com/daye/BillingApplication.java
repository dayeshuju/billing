package com.daye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@EnableCaching
@SpringBootApplication
@MapperScan("com.daye.sys.mapper")
@ServletComponentScan(basePackages = "com.daye.common.listener.RequestConLis")
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }
//    @Bean
//    public RequestContextListener requestContextListener(){
//        return new RequestContextListener();
//    }
}
