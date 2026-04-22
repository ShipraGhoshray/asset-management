package com.selflearning.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI assetOpenAPI() {
        //microservice:
            //http://localhost:8083/api/v3/api-docs
            //http://localhost:8083/api/swagger-ui/index.html#/
        //gateway:
            //http://localhost:8080/asset-service/api/v3/api-docs
            //http://localhost:8080/webjars/swagger-ui/index.html
        return new OpenAPI()
                .info(new Info()
                        .title("Asset Service API")
                        .version("1.0")
                        .description("API documentation for Asset Service"));
    }
}