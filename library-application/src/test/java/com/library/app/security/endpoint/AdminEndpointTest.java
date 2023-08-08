package com.library.app.security.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.app.services.registration.model.RegistrationRequest;
import com.library.app.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void createAdminAuthenticatedTest() throws Exception {
        //preparation
        RegistrationRequest request = getRegRequest();
        RequestBuilder requestBuilder = post("/api/v1/library/actions/admin/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        verify(service, times(1)).createAdmin(eq(request));
    }

    @Test
    void createAdminNotAuthenticatedTest() throws Exception {
        //preparation
        RegistrationRequest request = getRegRequest();
        RequestBuilder requestBuilder = post("/api/v1/library/actions/admin/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createAdminAuthenticatedNoAccessTest() throws Exception {
        //preparation
        RegistrationRequest request = getRegRequest();
        RequestBuilder requestBuilder = post("/api/v1/library/actions/admin/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //verification
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }


    private RegistrationRequest getRegRequest() {
        return new RegistrationRequest("fakeNAme", "fakePhone", "fakeEmail", "fakePassword");
    }
}
