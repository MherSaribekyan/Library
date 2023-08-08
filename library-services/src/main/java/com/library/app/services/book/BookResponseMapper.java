package com.library.app.services.book;

import com.library.app.repositories.persistence.Book;
import com.library.app.services.book.model.BookResponse;
import com.library.app.services.mapper.ResponseMapper;
import org.springframework.stereotype.Service;

@Service
public class BookResponseMapper implements ResponseMapper<BookResponse, Book> {
    @Override
    public BookResponse toResponse(Book book) {
        return new BookResponse(book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getDescription(),
                book.getIsbn(),
                book.getImage(),
                book.getPublished(),
                book.getPublisher());
    }
}
