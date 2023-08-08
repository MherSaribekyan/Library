package com.library.app.security.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.app.services.registration.RegistrationService;
import com.library.app.services.registration.model.RegistrationRequest;
import com.library.app.services.user.UserLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RegistrationEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrationTest() throws Exception {
        //preparation
        RegistrationRequest request = new RegistrationRequest("fakeNAme", "fakePhone", "fakeEmail", "fakePassword");
        RequestBuilder requestBuilder = post("/api/v1/library/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(service, times(1)).register(eq(request));
    }
}
