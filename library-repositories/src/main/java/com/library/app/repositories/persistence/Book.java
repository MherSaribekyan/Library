package com.library.app.repositories.persistence;

import com.library.app.repositories.persistence.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "t_book",
        uniqueConstraints = {@UniqueConstraint(name = "uk_book_id_deleted", columnNames = {"id", "deleted"})})
public class Book extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description")
    private String description;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "image")
    private String image;

    @Column(name = "published")
    private String published;

    @Column(name = "publisher")
    private String publisher;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Book book = (Book) o;

        return new EqualsBuilder()
                .appendSuper(true)
                .append(this.title, book.title)
                .append(this.author, book.author)
                .append(this.genre, book.genre)
                .append(this.description, book.description)
                .append(this.isbn, book.isbn)
                .append(this.image, book.image)
                .append(this.published, book.published)
                .append(this.publisher, book.publisher)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INIT_ODD, MULTIPLY_ODD)
                .appendSuper(super.hashCode())
                .append(this.title)
                .append(this.author)
                .append(this.genre)
                .append(this.description)
                .append(this.isbn)
                .append(this.image)
                .append(this.published)
                .append(this.publisher)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("title", this.title)
                .append("author", this.author)
                .append("genre", this.genre)
                .append("description", this.description)
                .append("isbn", this.isbn)
                .append("image", this.image)
                .append("published", this.published)
                .append("publisher", this.publisher)
                .toString();
    }


}
