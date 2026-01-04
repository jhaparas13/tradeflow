package com.paras.tradeflow.repository;

import ch.qos.logback.core.read.ListAppender;
import com.paras.tradeflow.entity.Product;
import com.paras.tradeflow.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus (ProductStatus status);
}
