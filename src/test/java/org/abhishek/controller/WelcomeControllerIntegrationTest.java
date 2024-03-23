package org.abhishek.controller;

import org.abhishek.repository.ProductRepository;
import org.abhishek.service.WelcomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author  : Abhishek
 * @since   : 2024-02-15, Thursday
 **/
@WebMvcTest(WelcomeController.class)
class WelcomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WelcomeService welcomeService;

    // this is not needed for this class but i have postConstruct method in the main class and when I run the mockMvc tests, i get error, hence i have included this for now.
    @MockBean
    private ProductRepository productRepository;

    @Test
    void shouldGetDefaultWelcomeMessage() throws Exception {
        when(welcomeService.getWelcomeMessage("Stranger"))
                .thenReturn("Welcome Stranger!");

        mockMvc.perform(get("/welcome"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Welcome Stranger!")));

        verify(welcomeService).getWelcomeMessage("Stranger");
    }

    @Test
    void shouldGetCustomWelcomeMessage() throws Exception {
        when(welcomeService.getWelcomeMessage("John"))
                .thenReturn("Welcome John!");

        mockMvc.perform(get("/welcome?name=John"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Welcome John!")));

        verify(welcomeService).getWelcomeMessage("John");
    }
}