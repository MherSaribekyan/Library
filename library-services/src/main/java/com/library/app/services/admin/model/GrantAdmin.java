package com.library.app.services.admin.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GrantAdmin(@NotBlank @Size(max = 64) String userId) {
}
