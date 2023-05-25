//package com.demo.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import springfox.documentation.builders.ApiInfoBuilder;
////import springfox.documentation.builders.PathSelectors;
////import springfox.documentation.builders.RequestHandlerSelectors;
////import springfox.documentation.service.ApiInfo;
////import springfox.documentation.spi.DocumentationType;
////import springfox.documentation.spring.web.plugins.Docket;
////import springfox.documentation.swagger2.annotations.EnableSwagger2;
////
////@Configuration
////@EnableSwagger2
////public class SwaggerConfig {
////
////    @Bean
////    public Docket api() {
////        return new Docket(DocumentationType.SWAGGER_2)
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.demo"))
////                .paths(PathSelectors.any())
////                .build()
////                .apiInfo(apiInfo());
////    }
////
////    private ApiInfo apiInfo() {
////        return new ApiInfoBuilder()
////                .title("Restaurant Food App")
////                .description("Food Items")
////                .version("1.0.0")
////                .build();
////    }
////}
//
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.context.annotation.Import;
//////import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
////import springfox.documentation.builders.ApiInfoBuilder;
////import springfox.documentation.builders.PathSelectors;
////import springfox.documentation.builders.RequestHandlerSelectors;
////import springfox.documentation.service.ApiInfo;
////import springfox.documentation.spi.DocumentationType;
//////import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
////import springfox.documentation.spring.web.plugins.Docket;
////import springfox.documentation.swagger2.annotations.EnableSwagger2;
//////import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
////
////
////@Configuration
//////@EnableSwagger2
////public class SwaggerConfig {
////
////    @Bean
////    public Docket schoolApi() {
////        return new Docket(DocumentationType.SWAGGER_2).
////                select().
////                apis(RequestHandlerSelectors.basePackage("com.demo")).
////                paths(PathSelectors.any()).
////                build();
////    }
////
////    private ApiInfo apiInfo() {
////        return new ApiInfoBuilder()
////                .title("Your API Title")
////                .description("Your API Description")
////                .version("1.0.0")
////                .build();
////    }
////}
//
//import liquibase.hub.model.ApiKey;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.core.context.SecurityContext;
//
//import java.util.Collections;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(Collections.singletonList(apiKey()))
//                .securityContexts(Collections.singletonList(securityContext()));
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", HttpHeaders.AUTHORIZATION, In.HEADER.name());
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(Collections.singletonList(new SecurityReference("JWT", new AuthorizationScope[0])))
//                .build();
//    }
//}
//
//
