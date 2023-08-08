package com.library.app.services.book.model;

import com.library.app.repositories.persistence.Book;

import java.util.List;

public record ApiBookResponse(String status,
                              String code,
                              String total,
                              List<Book> data) {
}
