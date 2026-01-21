package com.paras.tradeflow.service;

import com.paras.tradeflow.dto.ProductRequest;
import com.paras.tradeflow.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse getById(Long id);
    void deactivate(Long id);
    void activate(Long id);
    List<ProductResponse> getAllForAdmin();
    Page<ProductResponse> getAllForCustomer(Pageable pageable);
    boolean isInStock(Long productId, int quantity);
}
