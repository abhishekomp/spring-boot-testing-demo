package org.abhishek.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

/**
 * @author  : Abhishek
 * @since   : 2024-03-13, Wednesday
 **/
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    private String productCode;
    private double salePrice;
    private int discountPercentage;
    private String discountCode;
    private LocalDate discountStartDate;
    private LocalDate discountEndDate;

//    public Product(long productId, String productCode, double salePrice, int discountPercentage, String discountCode) {
//        this.productId = productId;
//        this.productCode = productCode;
//        this.salePrice = salePrice;
//        this.discountPercentage = discountPercentage;
//        this.discountCode = discountCode;
//    }

    public Product(){
        //no-args constructor
    }

    public Product(long productId, String productCode, double salePrice, int discountPercentage, String discountCode, LocalDate discountStartDate, LocalDate discountEndDate) {
        this.productId = productId;
        this.productCode = productCode;
        this.salePrice = salePrice;
        this.discountPercentage = discountPercentage;
        this.discountCode = discountCode;
        this.discountStartDate = discountStartDate;
        this.discountEndDate = discountEndDate;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public LocalDate getDiscountStartDate() {
        return discountStartDate;
    }

    public LocalDate getDiscountEndDate() {
        return discountEndDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", salePrice=" + salePrice +
                ", discountPercentage=" + discountPercentage +
                ", discountCode='" + discountCode + '\'' +
                ", discountStartDate=" + discountStartDate +
                ", discountEndDate=" + discountEndDate +
                '}';
    }
}
