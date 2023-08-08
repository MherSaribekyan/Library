package com.library.app.repositories.repo;


import com.library.app.repositories.persistence.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    Book getBookByTitleAndDeletedIsNull(String title);

    List<Book> findAllByDeletedIsNull(Pageable pageable);

    List<Book> findAllByAuthorAndDeletedIsNull(String author, Pageable pageable);
    List<Book> findAllByGenreAndDeletedIsNull(String genre, Pageable pageable);
}
