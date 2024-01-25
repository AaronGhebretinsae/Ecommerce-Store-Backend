package com.educative.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SwaggerConfig {

    /*@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.educative.ecommerce"))
                .paths(PathSelectors.any())
                .build();
    }*/

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Ecommerce API")
                        .description("Documentation Ecommerce api")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")));
    }

    /*private ApiInfo getApiInfo() {
        Contact contact = new Contact("webtutsplus", "http://webtutsplus.com", "contact.webtutsplus@gmail.com");
        return new ApiInfoBuilder()
                .title("Ecommerce API")
                .description("Documentation Ecommerce api")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }*/
}