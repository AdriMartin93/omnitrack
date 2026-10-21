package com.omnitrak.catalog_service.infrastructure.config;

import com.omnitrak.catalog_service.domain.annotations.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.omnitrak.catalog_service.application",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = UseCase.class
        )
)
public class UseCaseConfig {
}
