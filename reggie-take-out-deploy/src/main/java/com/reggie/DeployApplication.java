package com.reggie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@MapperScan("com.reggie.core.modular")
@PropertySource({"classpath:application-env.properties"})
@SpringBootApplication
public class DeployApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DeployApplication.class).run();
    }
}
