package com.yuemaz.security.loginsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@MapperScan("com.yuemaz.security.loginsecurity.mapper")
@SpringBootApplication
public class LoginSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginSecurityApplication.class, args);
    }

}
