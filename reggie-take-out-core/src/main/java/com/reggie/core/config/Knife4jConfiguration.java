package com.reggie.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfiguration {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(new ApiInfoBuilder().build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.reggie.core.modular"))
                .paths(PathSelectors.any())
                .build();
    }

}
