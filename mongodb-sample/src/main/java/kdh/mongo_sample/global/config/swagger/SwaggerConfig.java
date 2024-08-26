package kdh.mongo_sample.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "MongoDB-Sample API",
                version = "v1",
                description = "MongoDB-Sample API Docs"
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
