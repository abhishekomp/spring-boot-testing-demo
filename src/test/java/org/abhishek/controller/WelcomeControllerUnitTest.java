package org.abhishek.controller;

import org.abhishek.service.WelcomeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author  : Abhishek
 * @since   : 2024-02-15, Thursday
 **/
class WelcomeControllerUnitTest {

    @Test
    void welcome() {
        WelcomeService welcomeService = Mockito.mock(WelcomeService.class);
        when(welcomeService.getWelcomeMessage("John")).thenReturn("Welcome John!");
        WelcomeController welcomeController = new WelcomeController(welcomeService);
        assertEquals("Welcome John!", welcomeController.welcome("John"));
    }
}