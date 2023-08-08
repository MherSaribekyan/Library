package com.library.app.api.endpoints;

import com.library.app.services.admin.model.GrantAdmin;
import com.library.app.services.registration.model.RegistrationRequest;
import com.library.app.services.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library/actions/admin")
@Slf4j
public class AdminEndpoint {

    private final UserService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createAdmin(@Valid @RequestBody final RegistrationRequest request) {
        log.info("creating Admin account for : {}", request.name());
        this.service.createAdmin(request);
    }

    @PostMapping("/grant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void grantAdmin(@Valid @RequestBody final GrantAdmin grantAdmin) {
        log.info("grant admin access to user with id: {}", grantAdmin.userId());
        this.service.grantAdminAccess(grantAdmin);
    }

    public AdminEndpoint(final UserService service) {
        this.service = service;
    }
}
