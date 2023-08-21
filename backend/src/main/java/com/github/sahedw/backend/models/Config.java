package com.github.sahedw.backend.models;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import java.io.IOException;
@Configuration
public class Config implements WebMvcConfigurer {
    public static final String DEFAULT_STARTING_PAGE = "static/index.html";
    static class ReactRoutingPathResourceResolver extends PathResourceResolver {
        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {
            var requestedResource = location.createRelative(resourcePath);
            if (requestedResource.exists() && requestedResource.isReadable()) {
                return requestedResource;
            }
            return new ClassPathResource(DEFAULT_STARTING_PAGE);
        }
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new ReactRoutingPathResourceResolver());
    }
}