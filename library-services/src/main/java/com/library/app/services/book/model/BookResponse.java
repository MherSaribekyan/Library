package com.library.app.services.book.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookResponse(@NotBlank @Size(max = 64) String title,
                           @NotBlank @Size(max = 64)String author,
                           @NotBlank @Size(max = 64)String genre,
                           @NotBlank @Size(max = 512)String description,
                           @NotBlank @Size(max = 64)String isbn,
                           @NotBlank @Size(max = 64)String image,
                           @NotBlank @Size(max = 64)String published,
                           @NotBlank @Size(max = 64)String publisher) {
}
