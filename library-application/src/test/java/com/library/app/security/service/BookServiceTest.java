package com.library.app.security.service;

import com.library.app.repositories.persistence.Book;
import com.library.app.repositories.repo.BookRepository;
import com.library.app.services.book.BookResponseMapper;
import com.library.app.services.book.BookService;
import com.library.app.services.preferences.PreferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService service;

    @MockBean
    private BookRepository repository;

    @MockBean
    private PreferenceService preferenceService;

    @MockBean
    private BookResponseMapper responseMapper;

    @Test
    void saveAllTest() {
        //preparation
        List<Book> bookList = List.of(getBook());

        //source
        this.service.saveAll(bookList);

        //verification
        verify(this.repository, times(1)).saveAll(eq(bookList));
    }

    @Test
    void getBookTest() {
        //preparation
        Book book = getBook();
        when(this.repository.getBookByTitleAndDeletedIsNull(book.getTitle())).thenReturn(book);

        //source
        this.service.getBook(book.getTitle(), "fakePrincipalName");

        //verification
        verify(this.preferenceService, times(1))
                .addUserPreferences(eq("fakePrincipalName"), eq(List.of(book.getGenre(), book.getAuthor())));

        verify(this.responseMapper, times(1)).toResponse(any());
    }

    @Test
    void getBooksTest() {
        //preparation
        String principal = "fakePrincipalName";
        int pageNo = 0;
        int pageSize = 10;

        when(this.repository.findAllByDeletedIsNull(PageRequest.of(pageNo, pageSize))).thenReturn(List.of(getBook()));
        when(this.preferenceService.getUserPreferences(principal)).thenReturn(Set.of("preference1", "preference2"));

        //source
        assertDoesNotThrow(() -> this.service.getBooks(pageNo, pageSize, principal));
    }

    @Test
    void getBooksByAuthorTest() {
        //preparation
        String principal = "fakePrincipalName";
        String author = "fakeAuthor";
        int pageNo = 0;
        int pageSize = 10;

        when(this.repository.findAllByAuthorAndDeletedIsNull(author, PageRequest.of(pageNo, pageSize)))
                .thenReturn(List.of(getBook()));
        when(this.preferenceService.getUserPreferences(principal))
                .thenReturn(Set.of("preference1", "preference2"));

        //source
        assertDoesNotThrow(() -> this.service.getBooksByAuthor(author, principal, pageNo, pageSize));
    }

    @Test
    void getBooksByGenreTest() {
        //preparation
        String principal = "fakePrincipalName";
        String genre = "fakeGenre";
        int pageNo = 0;
        int pageSize = 10;

        when(this.repository.findAllByGenreAndDeletedIsNull(genre, PageRequest.of(pageNo, pageSize)))
                .thenReturn(List.of(getBook()));
        when(this.preferenceService.getUserPreferences(principal))
                .thenReturn(Set.of("preference1", "preference2"));

        //source
        assertDoesNotThrow(() -> this.service.getBooksByGenre(genre, principal, pageNo, pageSize));
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
