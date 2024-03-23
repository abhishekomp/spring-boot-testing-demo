package org.abhishek.service;

import org.abhishek.model.Product;
import org.abhishek.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author  : Abhishek
 * @since   : 2024-03-13, Wednesday
 **/
@Service
public class DiscountCalculatorService {

    private final ProductRepository productRepository;

    public DiscountCalculatorService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public double calculateDiscountedForProduct(String productCode, String discountCode){
        //check if the discount code is active on current date (further enhancement)
        return productRepository.findByProductCode(productCode)
                .filter(oProduct->(discountCode.equals(oProduct.getDiscountCode()) && LocalDate.now().isEqual(oProduct.getDiscountEndDate())) || (LocalDate.now().isAfter(oProduct.getDiscountStartDate()) && LocalDate.now().isBefore(oProduct.getDiscountEndDate())))
                .map(this::calculateDiscountedForProduct)
                .orElseThrow(() -> new RuntimeException("Cannot find product having productCode: " + productCode + " and discountCode: " + discountCode));
    }
    private double calculateDiscountedForProduct(Product product){
        double salePrice = product.getSalePrice();
        int discountPercentage = product.getDiscountPercentage();
        double discountedPrice = salePrice - ((salePrice * discountPercentage)/100.0);
        return discountedPrice;
    }

//    public double calculateDiscountedPrice(String productCode, String discountCode){
//
//        //Optional<Product> byId = productRepository.findById(4L);
//
//        Product product = productRepository.findByProductCode(productCode);
//
//        //check if the discount code is active on current date (further enhancement)
//        if((LocalDate.now().isEqual(product.getDiscountEndDate())) || (LocalDate.now().isAfter(product.getDiscountStartDate()) && LocalDate.now().isBefore(product.getDiscountEndDate()))){
//            double salePrice = product.getSalePrice();
//            int discountPercentage = product.getDiscountPercentage();
//            double discountedPrice = salePrice - ((salePrice * discountPercentage)/100.0);
//            return discountedPrice;
//        }
//        return product.getSalePrice();
//    }

    public List<Product> getProductsByDiscountPercentage(int discountPercentage){
        return productRepository.findByDiscountPercentage(discountPercentage);
    }
}
