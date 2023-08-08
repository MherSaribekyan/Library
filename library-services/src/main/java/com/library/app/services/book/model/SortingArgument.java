package com.library.app.services.book.model;


import lombok.Getter;

@Getter
public enum SortingArgument {

    GENRE("genre"),
    AUTHOR("author");

    private final String name;

    SortingArgument(String name) {
        this.name = name;
    }
}
