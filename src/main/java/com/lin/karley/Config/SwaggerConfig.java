package com.lin.karley.Config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/19
 */
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("karley")
                .select().apis(RequestHandlerSelectors.basePackage("com.lin.karley.Controller")).build();
    }

    private ApiInfo apiInfo()
    {
        new Contact("karley","http://karley.com","361983537@qq.com");
        return new ApiInfo("karley的接口日记","Api Documentation","1.0","urn:tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }
}
