package com.library.app.services.book;

import com.library.app.repositories.persistence.Book;
import com.library.app.repositories.repo.BookRepository;
import com.library.app.services.book.model.BookResponse;
import com.library.app.services.book.model.SortingArgument;
import com.library.app.services.preferences.PreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class LibraryBookService implements BookService {

    private final BookRepository repository;

    private final BookResponseMapper responseMapper;

    private final PreferenceService preferenceService;


    @Override
    public void saveAll(final List<Book> books) {
        this.repository.saveAll(books);
    }

    @Override
    public BookResponse getBook(final String title, final String principalName) {
        final Book bookByTitle = this.repository.getBookByTitleAndDeletedIsNull(title);
        this.preferenceService.addUserPreferences(principalName, List.of(bookByTitle.getGenre(), bookByTitle.getAuthor()));
        return this.responseMapper.toResponse(bookByTitle);
    }

    @Override
    public List<BookResponse> getBooks(final Integer pageNo, final Integer pageSize, final String principalName) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        final List<Book> books = this.repository.findAllByDeletedIsNull(pageable);
        final Set<String> userPreferences = preferenceService.getUserPreferences(principalName);
        final List<Book> sorted = userPreferences.isEmpty() ? books : this.sort(books, userPreferences, null);
        return sorted.stream()
                .map(this.responseMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookResponse> getBooksByAuthor(final String author, final String principalName, final Integer pageNo, final Integer pageSize) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Book> allByAuthor = this.repository.findAllByAuthorAndDeletedIsNull(author, pageable);
        final Set<String> userPreferences = preferenceService.getUserPreferences(principalName);
        final List<Book> sorted = userPreferences.isEmpty() ? allByAuthor : this.sort(allByAuthor, userPreferences, SortingArgument.GENRE);
        this.preferenceService.addUserPreference(principalName, author);
        return sorted.stream()
                .map(this.responseMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookResponse> getBooksByGenre(final String genre, final String principalName, final Integer pageNo, final Integer pageSize) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        final List<Book> allByGenre = this.repository.findAllByGenreAndDeletedIsNull(genre, pageable);
        final Set<String> userPreferences = preferenceService.getUserPreferences(principalName);
        final List<Book> sorted = userPreferences.isEmpty() ? allByGenre : this.sort(allByGenre, userPreferences, SortingArgument.AUTHOR);
        this.preferenceService.addUserPreference(principalName, genre);
        return sorted.stream()
                .map(this.responseMapper::toResponse)
                .toList();
    }

    private List<Book> sort(final List<Book> books, final Set<String> userPreferences, final SortingArgument sortArgument) {
        final List<Book> sorted = new ArrayList<>(books.size());
        final Iterator<Book> iterator = books.iterator();
        final List<String> argumentList = new ArrayList<>();
        switch (sortArgument) {
            case GENRE -> {
                argumentList.add(SortingArgument.GENRE.getName());
            }
            case AUTHOR -> {
                argumentList.add(SortingArgument.AUTHOR.getName());
            }
            default -> {
                argumentList.add(SortingArgument.AUTHOR.getName());
                argumentList.add(SortingArgument.GENRE.getName());
            }
        }

        while (iterator.hasNext()) {
            final Book next = iterator.next();
            if (userPreferences.containsAll(argumentList)) {
                sorted.add(next);
                iterator.remove();
            }
        }

        sorted.addAll(books);
        return sorted;
    }

    public LibraryBookService(final BookRepository repository,
                              final BookResponseMapper responseMapper,
                              final PreferenceService preferenceService) {
        this.repository = repository;
        this.responseMapper = responseMapper;
        this.preferenceService = preferenceService;
    }
}
