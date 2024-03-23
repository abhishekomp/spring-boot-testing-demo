package org.abhishek.controller;

import org.abhishek.domain.WelcomeClass;
import org.abhishek.service.WelcomeService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/welcome/post")
    public WelcomeClass welcomePost(@RequestBody WelcomeClass welcomeClass){
        System.out.println("WelcomeController: welcomePost()");
        return new WelcomeClass(welcomeClass.getName());
    }
}
