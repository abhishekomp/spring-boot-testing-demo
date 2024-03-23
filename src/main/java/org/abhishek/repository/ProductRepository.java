package org.abhishek.repository;

import org.abhishek.model.Product;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author  : Abhishek
 * @since   : 2024-03-13, Wednesday
 **/
//@Repository
//public interface ProductRepository extends CrudRepository<Product, Long>{
//
//    Product findByProductCode(String productCode);
//}

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);
    //Product findByProductCode(String productCode);
    List<Product> findByDiscountPercentage(int discountPercentage);
}
