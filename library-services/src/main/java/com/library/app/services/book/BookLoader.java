package com.library.app.services.book;

import com.library.app.services.book.model.ApiBookResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
@Slf4j
public class BookLoader {
    private final BookService service;
    private final RestTemplate restTemplate;


    @PostConstruct
    @Transactional
    public void loadBooks() {
        final String url = "https://fakerapi.it/api/v1/books?_quantity=100&_locale=en_US";
        final ResponseEntity<ApiBookResponse> response = restTemplate
                .getForEntity(url, ApiBookResponse.class);
        this.service.saveAll(Objects.requireNonNull(response.getBody()).data());
    }

    public BookLoader(final BookService service,
                      final RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }
}
