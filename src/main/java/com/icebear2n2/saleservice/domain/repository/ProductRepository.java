package com.icebear2n2.saleservice.domain.repository;

import com.icebear2n2.saleservice.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByProductId(Long productId);
    boolean existsByProductName(String ProductName);
    Product findByProductName(String productName);

    List<Product> findBySaleStartDateBeforeAndSaleEndDateAfter(Timestamp currentTimestamp, Timestamp currentTimestamp1);
}
