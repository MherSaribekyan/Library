package com.library.app.api.endpoints;

import com.library.app.services.book.BookService;
import com.library.app.services.book.model.BookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/library/books")
@Slf4j
public class BookEndpoint {

    private final BookService bookService;

    @GetMapping
    public List<BookResponse> getBooks(@RequestParam(defaultValue = "0") final Integer pageNo,
                                       @RequestParam(defaultValue = "20") final Integer pageSize,
                                       final Principal principal) {
        log.info("getting books");
        return this.bookService.getBooks(pageNo, pageSize, principal.getName());
    }

    @GetMapping("/title/{title}")
    public BookResponse getBookByTitle(@PathVariable final String title, final Principal principal) {
        log.info("getting book by title: {}", title);
        return this.bookService.getBook(title, principal.getName());
    }

    @GetMapping("/author/{author}")
    public List<BookResponse> getBooksByAuthor(@RequestParam(defaultValue = "0") final Integer pageNo,
                                               @RequestParam(defaultValue = "20") final Integer pageSize,
                                               @PathVariable final String author, final Principal principal) {
        log.info("getting book by author: {}", author);
        return this.bookService.getBooksByAuthor(author, principal.getName(), pageNo, pageSize);
    }

    @GetMapping("/genre/{genre}")
    public List<BookResponse> getBooksByGenre(@RequestParam(defaultValue = "0") final Integer pageNo,
                                               @RequestParam(defaultValue = "20") final Integer pageSize,
                                               @PathVariable final String genre, final Principal principal) {
        log.info("getting book by genre: {}", genre);
        return this.bookService.getBooksByGenre(genre, principal.getName(), pageNo, pageSize);
    }



    public BookEndpoint(final BookService bookService) {
        this.bookService = bookService;
    }
}
