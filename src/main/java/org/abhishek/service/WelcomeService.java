package org.abhishek.service;

import org.springframework.stereotype.Service;

/**
 * @author  : Abhishek
 * @since   : 2024-02-15, Thursday
 **/
@Service
public class WelcomeService {
    public String getWelcomeMessage(String name){
        return String.format("Welcome %s!", name);
    }
}
