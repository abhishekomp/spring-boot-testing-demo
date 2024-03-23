package org.abhishek.exception;

/**
 * @author : Abhishek
 * @since : 2024-03-22, Friday
 **/
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String s) {
        super(s);
    }
}
