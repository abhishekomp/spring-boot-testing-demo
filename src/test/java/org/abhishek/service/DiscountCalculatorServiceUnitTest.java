package org.abhishek.service;

import org.abhishek.model.Product;
import org.abhishek.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author : Abhishek
 * @since : 2024-03-13, Wednesday
 **/
class DiscountCalculatorServiceUnitTest {

//    private DiscountCalculatorService discountCalculatorService;

//    @BeforeEach
//    void setUp() {
//        discountCalculatorService = new DiscountCalculatorService(null);
//    }

    @Test
    void calculateDiscount_for_valid_discount_period() {
        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.of(2024, 02, 15), LocalDate.of(2024, 03, 31));
        //Optional<Product> product = Optional.of(product);

        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(Optional.of(product));

        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
        double discountedPrice = discountCalculatorService.calculateDiscountedForProduct(product.getProductCode(), product.getDiscountCode());
        assertEquals(16199.1, discountedPrice);
    }

    @Test
    void calculateDiscount_for_past_discount_period() {
        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.of(2024, 02, 15), LocalDate.of(2024, 02, 29));

        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(Optional.of(product));

        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
        //double discountedPrice = discountCalculatorService.calculateDiscountedForProduct(product.getProductCode(), product.getDiscountCode());
        Throwable exception = assertThrows(RuntimeException.class, () -> discountCalculatorService.calculateDiscountedForProduct(product.getProductCode(), product.getDiscountCode()));
        assertNotNull(exception);
        assertEquals("Cannot find product having productCode: " + product.getProductCode() + " and discountCode: " + product.getDiscountCode(), exception.getMessage());
        //assertEquals(17999.0, discountedPrice);
    }

    @Test
    void calculateDiscount_when_today_is_the_last_day_for_discount() {
        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.of(2024, 02, 15), LocalDate.now());

        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(Optional.of(product));

        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
        double discountedPrice = discountCalculatorService.calculateDiscountedForProduct(product.getProductCode(), product.getDiscountCode());
        assertEquals(16199.1, discountedPrice);
    }

    @Test
    void calculateDiscount_should_not_apply_when_discount_starts_in_future() {
        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));

        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(Optional.of(product));

        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
        //double discountedPrice = discountCalculatorService.calculateDiscountedForProduct(product.getProductCode(), product.getDiscountCode());
        Throwable exception = assertThrows(RuntimeException.class, () -> discountCalculatorService.calculateDiscountedForProduct(product.getProductCode(), product.getDiscountCode()));
        assertNotNull(exception);
        assertEquals("Cannot find product having productCode: " + product.getProductCode() + " and discountCode: " + product.getDiscountCode(), exception.getMessage());
        //assertEquals(17999.0, discountedPrice);
    }

    @Test
    void calculateDiscount_should_not_apply_when_discount_starts_today_and_ends_today() {
        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.now(), LocalDate.now());

        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(Optional.of(product));

        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
        double discountedPrice = discountCalculatorService.calculateDiscountedForProduct(product.getProductCode(), product.getDiscountCode());
        assertEquals(16199.1, discountedPrice);
    }

//    @Test
//    void calculateDiscount_for_valid_discount_period() {
//        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.of(2024, 02, 15), LocalDate.of(2024, 03, 31));
//        //Optional<Product> product = Optional.of(product);
//
//        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
//        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(product);
//
//        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
//        double discountedPrice = discountCalculatorService.calculateDiscountedPrice(product.getProductCode(), product.getDiscountCode());
//        assertEquals(16199.1, discountedPrice);
//    }
//
//    @Test
//    void calculateDiscount_for_past_discount_period() {
//        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.of(2024, 02, 15), LocalDate.of(2024, 02, 29));
//
//        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
//        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(product);
//
//        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
//        double discountedPrice = discountCalculatorService.calculateDiscountedPrice(product.getProductCode(), product.getDiscountCode());
//        assertEquals(17999.0, discountedPrice);
//    }
//
//    @Test
//    void calculateDiscount_when_today_is_the_last_day_for_discount() {
//        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.of(2024, 02, 15), LocalDate.now());
//
//        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
//        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(product);
//
//        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
//        double discountedPrice = discountCalculatorService.calculateDiscountedPrice(product.getProductCode(), product.getDiscountCode());
//        assertEquals(16199.1, discountedPrice);
//    }
//
//    @Test
//    void calculateDiscount_should_not_apply_when_discount_starts_in_future() {
//        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
//
//        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
//        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(product);
//
//        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
//        double discountedPrice = discountCalculatorService.calculateDiscountedPrice(product.getProductCode(), product.getDiscountCode());
//        assertEquals(17999.0, discountedPrice);
//    }
//
//    @Test
//    void calculateDiscount_should_not_apply_when_discount_starts_today_and_ends_today() {
//        Product product = new Product(100001L, "APPLE_iPHONE_2023_256GB", 17999.0, 10, "WELCOME_SPRING", LocalDate.now(), LocalDate.now());
//
//        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
//        when(productRepository.findByProductCode("APPLE_iPHONE_2023_256GB")).thenReturn(product);
//
//        DiscountCalculatorService discountCalculatorService = new DiscountCalculatorService(productRepository);
//        double discountedPrice = discountCalculatorService.calculateDiscountedPrice(product.getProductCode(), product.getDiscountCode());
//        assertEquals(16199.1, discountedPrice);
//    }
}