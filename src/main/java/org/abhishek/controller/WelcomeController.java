package org.abhishek.controller;

import org.abhishek.service.WelcomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  : Abhishek
 * @since   : 2024-02-15, Thursday
 **/
@RestController
public class WelcomeController {

    private WelcomeService welcomeService;

    public WelcomeController(WelcomeService welcomeService){
        this.welcomeService = welcomeService;
    }

    @GetMapping("/welcome")
    public String welcome(@RequestParam(defaultValue = "Stranger") String name){
        return welcomeService.getWelcomeMessage(name);
    }
}
