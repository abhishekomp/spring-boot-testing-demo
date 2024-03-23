package org.abhishek.domain;

/**
 * @author  : Abhishek
 * @since   : 2024-02-19, Monday
 **/
public class WelcomeClass {
    private final String name;
    private final String msg;

    public WelcomeClass(){
        this.name = "";
        this.msg = "";
    }
    public WelcomeClass(String name){
        this.name = name;
        this.msg = "How are you";
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "WelcomeClass{" +
                "name='" + name + '\'' +
                '}';
    }
}
