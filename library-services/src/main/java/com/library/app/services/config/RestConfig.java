package com.library.app.services.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class RestConfig {

    @SuppressWarnings("MagicNumber")
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder templateBuilder) {
        templateBuilder.requestFactory((Supplier<ClientHttpRequestFactory>) HttpComponentsClientHttpRequestFactory::new)
                .setConnectTimeout(Duration.ofMillis(20000));
        return templateBuilder.build();
    }

}
