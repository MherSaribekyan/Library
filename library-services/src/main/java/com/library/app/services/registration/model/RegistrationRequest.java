package com.library.app.services.registration.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(@NotBlank @Size(max = 64) String name,
                                  @NotBlank @Size(max = 64) String phone,
                                  @NotBlank @Size(max = 64) String email,
                                  @NotBlank @Size(max = 64) String password) {
}
