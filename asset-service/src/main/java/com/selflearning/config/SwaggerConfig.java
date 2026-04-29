package com.selflearning.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Service API", version = "1.0"),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    //microservice:
        // ?? http://localhost:8083/api/v3/api-docs
        // ?? http://localhost:8083/api/swagger-ui/index.html#/

        //http://localhost:8083/v3/api-docs
        //http://localhost:8080/asset-service/v3/api-docs
        // http://localhost:8083/swagger-ui/index.html#/
    //gateway:
        //http://localhost:8080/asset-service/api/v3/api-docs
        //http://localhost:8080/webjars/swagger-ui/index.html

    /*@Bean
    public OpenAPI assetOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Asset Service API")
                        .version("1.0")
                        .description("API documentation for Asset Service"));
    }*/
}