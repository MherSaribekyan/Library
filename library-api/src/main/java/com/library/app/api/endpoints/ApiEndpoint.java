package com.library.app.api.endpoints;

import com.google.common.io.CharStreams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class ApiEndpoint {
    @Value("classpath:openapi.yml")
    private Resource openapiResource;
    
    @SneakyThrows
    @GetMapping(value = "openapi.yml", produces = "text/yaml")
    public String openapi() {
        return CharStreams.toString(new InputStreamReader(openapiResource.getInputStream(), StandardCharsets.UTF_8));
    }
}
