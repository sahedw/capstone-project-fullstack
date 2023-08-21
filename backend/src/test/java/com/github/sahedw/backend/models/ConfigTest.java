package com.github.sahedw.backend.models;

import com.github.sahedw.backend.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest({Config.class, SecurityConfig.class})
@EnableWebSecurity
@AutoConfigureMockMvc
class ConfigTest {

    @Autowired
    private MockMvc mockMvc;

    private Config config;

    @Mock
    private ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);

    @BeforeEach
    public void setUp() {
        config = new Config();
    }

    @Test
    void expectResourceHandlerInRegistry_whenHandlerIsAdded() {
        //GIVEN
        ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);
        ResourceChainRegistration chainRegistration = mock(ResourceChainRegistration.class);
        //WHEN
        when(registry.addResourceHandler(any(String.class))).thenReturn(registration);
        when(registration.addResourceLocations(any(String[].class))).thenReturn(registration);
        when(registration.resourceChain(any(Boolean.class))).thenReturn(chainRegistration);
        when(chainRegistration.addResolver(any(PathResourceResolver.class))).thenReturn(chainRegistration);
        config.addResourceHandlers(registry);
        //THEN
        verify(registry).addResourceHandler("/**");
        verify(registration).addResourceLocations("classpath:/static/");
        verify(registration).resourceChain(true);
        verify(chainRegistration).addResolver(any(PathResourceResolver.class));
    }
}