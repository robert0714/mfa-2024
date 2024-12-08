package com.example.mfa.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition; 
import io.swagger.v3.oas.annotations.info.Info; 
  
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 

@OpenAPIDefinition( 
        info = @Info(
            title = "Ocarbon MFA DEMO API",
            description = "MFA DEMO API",
            version = "v1")) 
@Configuration
public class OpenApiConfig {
 

    private static final String[] authPaths = {
    		"/calculate/**" ,        
    };
 
 

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch(authPaths)
                .build();
    } 
}
