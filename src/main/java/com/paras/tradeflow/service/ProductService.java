package com.paras.tradeflow.service;

import com.paras.tradeflow.dto.ProductRequest;
import com.paras.tradeflow.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse getById(Long id);
    void deactivate(Long id);
    void activate(Long id);
    List<ProductResponse> getAllForAdmin();
    List<ProductResponse> getAllForCustomer();
    boolean isInStock(Long productId, int quantity);
}
