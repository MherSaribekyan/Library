package com.library.app.api.endpoints;

import com.library.app.services.registration.RegistrationService;
import com.library.app.services.registration.model.RegistrationRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library/registration")
@Slf4j
public class RegistrationEndpoint {

    private final RegistrationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@Valid @RequestBody final RegistrationRequest request) {
        log.info("Got request about registration for user: {}", request.email());
        service.register(request);
    }

    public RegistrationEndpoint(final RegistrationService service) {
        this.service = service;
    }
}
