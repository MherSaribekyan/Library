package com.library.app.security.repository;

import com.library.app.repositories.persistence.Book;
import com.library.app.repositories.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    void saveBookTest() {
        //preparation
        Book book = getBook();

        //source
        Book save = this.repository.save(book);

        //verification
        Optional<Book> byId = this.repository.findById(save.getId());
        assertTrue(byId.isPresent());
    }

    @Test
    void getBooksTest() {
        //preparation
        Pageable pageable = PageRequest.of(0, 10);
        //source
        List<Book> allByDeletedIsNull = this.repository.findAllByDeletedIsNull(pageable);

        //verification
        assertEquals(10, allByDeletedIsNull.size());
    }

    @Test
    void getBookByTitleAndDeletedIsNullTest() {
        //preparation
        Book book = getBook();
        this.repository.save(book);

        //source
        Book bookByTitleAndDeletedIsNull = this.repository.getBookByTitleAndDeletedIsNull(book.getTitle());

        //verification
        assertNotNull(bookByTitleAndDeletedIsNull);
    }

    @Test
    void getBooksByAuthorTest() {
        //verification
        assertDoesNotThrow(() -> this.repository.findAllByAuthorAndDeletedIsNull("fakeAuthor", PageRequest.of(0, 10)));
    }

    @Test
    void getBooksByGenreTest() {
        //verification
        assertDoesNotThrow(() -> this.repository.findAllByGenreAndDeletedIsNull("fakeGenre", PageRequest.of(0, 10)));
    }

    private Book getBook() {
        Book book = new Book();
        book.setTitle("fakeTitle");
        book.setAuthor("fakeAuthor");
        book.setDescription("fakeDesc");
        book.setIsbn("fakeISBN");
        book.setImage("fakeImage");
        book.setGenre("fakeGenre");
        book.setPublished("fakePublished");
        book.setPublisher("fakePublisher");
        return book;
    }

}
