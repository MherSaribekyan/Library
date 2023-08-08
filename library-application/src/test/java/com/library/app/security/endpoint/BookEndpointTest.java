package com.library.app.security.endpoint;

import com.library.app.services.book.BookLoader;
import com.library.app.services.book.BookService;
import com.library.app.services.user.UserLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @SuppressWarnings("unused")
    @MockBean
    private BookLoader bookLoader;

    @Test
    @WithMockUser(roles = "USER")
    void getBooksTest() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/library/books")
                .queryParam("pageNo", "0")
                .queryParam("pageSize", "10");

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).getBooks(eq(0), eq(10), any());
    }

    @Test
    void getBooksNotAuthenticatedTest() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/library/books")
                .queryParam("pageNo", "0")
                .queryParam("pageSize", "10");

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getBookByTitleTest() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/library/books/title/fakeTitle");

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getBooksByAuthorTest() throws Exception {
        String author = "fakeAuthor";
        RequestBuilder requestBuilder = get("/api/v1/library/books/author/" + author)
                .queryParam("pageNo", "0")
                .queryParam("pageSize", "10");

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).getBooksByAuthor(eq(author), any(), eq(0), eq(10));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getBooksByGenreTest() throws Exception {
        String genre = "fakeGenre";
        RequestBuilder requestBuilder = get("/api/v1/library/books/genre/" + genre)
                .queryParam("pageNo", "0")
                .queryParam("pageSize", "10");

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).getBooksByGenre(eq(genre), any(), eq(0), eq(10));
    }
}
