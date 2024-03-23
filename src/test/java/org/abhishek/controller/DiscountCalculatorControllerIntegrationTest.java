package org.abhishek.controller;

import jakarta.validation.ConstraintViolationException;
import org.abhishek.exception.ProductNotFoundException;
import org.abhishek.model.Product;
import org.abhishek.repository.ProductRepository;
import org.abhishek.service.DiscountCalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author : Abhishek
 * @since : 2024-03-22, Friday
 **/
@WebMvcTest(DiscountCalculatorController.class)
class DiscountCalculatorControllerIntegrationTest {

//    @BeforeEach
//    void setUp() {
//    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountCalculatorService discountCalculatorService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("Get endpoint accessed without providing the query parameter will consider the default value for the query parameter")
    void getProductsByDiscountPercentage_should_return_single_product_when_endpoint_accessed_without_percentage_value() throws Exception {
        Product appleIPhone2020 = new Product(100005L, "APPLE_iPHONE_2020_256GB", 13999.0, 10, "WELCOME_SPRING", LocalDate.now(), LocalDate.now());
        when(discountCalculatorService.getProductsByDiscountPercentage(10))
                .thenReturn(List.of(appleIPhone2020));
//        when(discountCalculatorService.getProductsByDiscountPercentage(eq(10)))
//                .thenReturn(products);

        // Learning: was not working with just the path mentioned on the controller method. Started working when I mentioned the full path i.e. controller + controller's method
        mockMvc.perform(get("/api/discount/byPercentage"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("my-custom-header", "my-custom-value_20"))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].productCode", is("APPLE_iPHONE_2020_256GB")));

        verify(discountCalculatorService).getProductsByDiscountPercentage(10);
    }

    @Test
    @DisplayName("Get endpoint accessed with value for query parameter that returns 1 record")
    void getProductsByDiscountPercentage_should_return_single_product_for_percentage_equals_20() throws Exception {
        Product appleIPhone1999 = new Product(100005L, "APPLE_iPHONE_1999_256GB", 13999.0, 20, "WELCOME_SPRING", LocalDate.now(), LocalDate.now());
        when(discountCalculatorService.getProductsByDiscountPercentage(20))
                .thenReturn(List.of(appleIPhone1999));

        mockMvc.perform(get("/api/discount/byPercentage").param("discountPercentage", "20"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].productCode", is("APPLE_iPHONE_1999_256GB")));

        verify(discountCalculatorService).getProductsByDiscountPercentage(20);
    }

    @Test
    @DisplayName("Get endpoint accessed with value for query parameter that returns more than 1 record")
    void getProductsByDiscountPercentage_should_return_2_products_for_given_discount_percentage() throws Exception {
        Product appleIPhone1999 = new Product(100005L, "APPLE_iPHONE_1999_256GB", 13999.0, 20, "WELCOME_SPRING", LocalDate.now(), LocalDate.now());
        Product appleIPhone2000 = new Product(100006L, "APPLE_iPHONE_2000_256GB", 14499.0, 20, "WELCOME_SPRING", LocalDate.now(), LocalDate.now().plusDays(1));
        when(discountCalculatorService.getProductsByDiscountPercentage(20))
                .thenReturn(List.of(appleIPhone1999, appleIPhone2000));

        mockMvc.perform(get("/api/discount/byPercentage").param("discountPercentage", "20"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[*].productId", containsInAnyOrder(100006, 100005)))
                .andExpect(jsonPath("$[*].productCode", containsInAnyOrder("APPLE_iPHONE_1999_256GB","APPLE_iPHONE_2000_256GB")));
        //.andExpect(jsonPath("$[0].productCode", is("APPLE_iPHONE_1999_256GB")))
        //.andExpect(jsonPath("$[1].productCode", is("APPLE_iPHONE_2000_256GB")))
        // can also be written as .andExpect(jsonPath("$[*].productId").value(containsInAnyOrder(100005, 100006)))
        verify(discountCalculatorService).getProductsByDiscountPercentage(20);
    }

    @Test
    @DisplayName("Get endpoint throws custom exception when no records exist for the provided query parameter")
    void test_should_throw_ProductNotFoundException_when_no_products_exist_for_given_percentage() throws Exception {
        when(discountCalculatorService.getProductsByDiscountPercentage(60))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/discount/byPercentage").param("discountPercentage", "60"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.header().string("my-custom-header", "my-custom-value-exception"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(result -> assertEquals("No product(s) found for discountPercentage 60", result.getResolvedException().getMessage()))
                .andExpect(jsonPath("$.errorMsg", is("No product(s) found for discountPercentage 60")));

        verify(discountCalculatorService).getProductsByDiscountPercentage(60);
    }

    @Test
    @DisplayName("Get endpoint access throws ConstraintViolation Exception for a int query parameter")
    void test_should_throw_ConstraintViolationException_when_no_products_exist_for_given_percentage_600() throws Exception {

        mockMvc.perform(get("/api/discount/byPercentage").param("discountPercentage", "600"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.header().string("my-custom-header", "my-custom-value-invalid-input"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("Invalid discount percentage")))
                .andExpect(jsonPath("$.errorMsg", is("Invalid discount percentage")));

        //verify(discountCalculatorService).getProductsByDiscountPercentage(60);
        //mock service was not invoked
        verify(discountCalculatorService, times(0)).getProductsByDiscountPercentage(60);
    }
}