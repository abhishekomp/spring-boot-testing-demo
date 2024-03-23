package org.abhishek.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.abhishek.exception.ProductNotFoundException;
import org.abhishek.model.ApiError;
import org.abhishek.model.Product;
import org.abhishek.service.DiscountCalculatorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * @author  : Abhishek
 * @since   : 2024-03-13, Wednesday
 **/
@RestController
@Validated
@RequestMapping("/api/discount")
public class DiscountCalculatorController {

    private DiscountCalculatorService discountCalculatorService;

    public DiscountCalculatorController(DiscountCalculatorService discountCalculatorService) {
        this.discountCalculatorService = discountCalculatorService;
    }

    //http://localhost:8080/api/discount/get-discounted-price?productCode=APPLE_iPHONE_2024_256GB&discountCode=WELCOME_SPRING
    @GetMapping("/get-discounted-price")
    public double getDiscountedPrice(@RequestParam String productCode, @RequestParam String discountCode){
        double discountedPrice = discountCalculatorService.calculateDiscountedForProduct(productCode, discountCode);
        System.out.println("Received discount price: " + discountedPrice);
        return discountedPrice;
    }

    //http://localhost:8080/api/discount/byPercentage?discountPercentage=20
    //jakarta.validation.ConstraintViolationException: getProductsByDiscountPercentage.discountPercentage: Invalid discount percentage
    @GetMapping("/byPercentage")
    public ResponseEntity getProductsByDiscountPercentage(
            @RequestParam(required = false, defaultValue = "10")
            @Max(value = 100, message = "Invalid discount percentage")
            @Min(value = 0, message = "Invalid discount percentage")
            int discountPercentage){
        List<Product> productsByDiscountPercentage = discountCalculatorService.getProductsByDiscountPercentage(discountPercentage);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("my-custom-header", "my-custom-value_20");
        if(!productsByDiscountPercentage.isEmpty()){
            return ResponseEntity.ok().headers(responseHeaders).body(productsByDiscountPercentage);
            //return new ResponseEntity(productsByDiscountPercentage, responseHeaders, HttpStatus.OK);
        } else {
            System.out.println(">>>>>> DiscountCalculatorController::getProductsByDiscountPercentage()-> will throw ProductNotFoundException::class");
            throw new ProductNotFoundException("No product(s) found for discountPercentage " + discountPercentage);
        }
        //return ResponseEntity.notFound().build();
        //return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex){
        System.out.println(">>>>>> DiscountCalculatorController::handleProductNotFoundException()");
        ApiError errObj = new ApiError();
        errObj.setErrCode(HttpStatus.NOT_FOUND.value());
        errObj.setErrorMsg(ex.getMessage());
        errObj.setErrorDateTime(LocalDateTime.now());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("my-custom-header", "my-custom-value-exception");
        //return ResponseEntity.notFound().headers(responseHeaders).body(errObj);
        //return ResponseEntity.ok().headers(responseHeaders).body(errObj);
        return new ResponseEntity<>(errObj, responseHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            System.out.println(violation.getPropertyPath().toString() + "-> " + violation.getMessage());
        }
        ApiError errObj = new ApiError();
        errObj.setErrCode(HttpStatus.BAD_REQUEST.value());
        errObj.setErrorMsg(e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(joining(", ")));
        errObj.setErrorDateTime(LocalDateTime.now());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("my-custom-header", "my-custom-value-invalid-input");
        return new ResponseEntity<>(errObj, responseHeaders, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex){
//        System.out.println(">>>>>> DiscountCalculatorController::handleConstraintViolationException()");
//        ApiError errObj = new ApiError();
//        errObj.setErrCode(HttpStatus.BAD_REQUEST.value());
//        errObj.setErrorMsg(ex.getMessage());
//        errObj.setCurrentDateTime(LocalDateTime.now());
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("my-custom-header", "my-custom-value");
//        //return ResponseEntity.notFound().headers(responseHeaders).body(errObj);
//        //return ResponseEntity.ok().headers(responseHeaders).body(errObj);
//        return new ResponseEntity<>(errObj, HttpStatus.BAD_REQUEST);
//    }

//    //http://localhost:8080/api/discount/byPercentage?discountPercentage=20
//    @GetMapping("/byPercentage")
//    public ResponseEntity getProductsByDiscountPercentage(@RequestParam int discountPercentage){
//        List<Product> productsByDiscountPercentage = discountCalculatorService.getProductsByDiscountPercentage(discountPercentage);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("my-custom-header", "my-custom-value");
//        if(!productsByDiscountPercentage.isEmpty()){
//            //return ResponseEntity.ok().headers(responseHeaders).body(productsByDiscountPercentage);
//            return new ResponseEntity(productsByDiscountPercentage, responseHeaders, HttpStatus.OK);
//        }
//        return ResponseEntity.notFound().build();
//        //return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }
}

