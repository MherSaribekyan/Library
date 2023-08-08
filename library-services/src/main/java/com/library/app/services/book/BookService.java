package com.library.app.services.book;

import com.library.app.repositories.persistence.Book;
import com.library.app.services.book.model.BookResponse;

import java.util.List;

public interface BookService {

    void saveAll(List<Book> books);

    BookResponse getBook(String title, String principalName);

    List<BookResponse> getBooks(Integer pageNo, Integer pageSize, String principalName);

    List<BookResponse> getBooksByAuthor(String author, String principalName, Integer pageNo, Integer pageSize);

    List<BookResponse> getBooksByGenre(String genre, String principalName, Integer pageNo, Integer pageSize);
}
