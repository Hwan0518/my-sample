package egenius.orders.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Gentledog Orders API",
                version = "v1",
                description = "Order-service API Docs for Gentledog"
        )
)

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
        public GroupedOpenApi publicApi() {
            String[] paths = {"/api/v1/**"};
            return GroupedOpenApi.builder()
                    .group("public-api")
                    .pathsToMatch(paths)
                    .build();
    }
}
