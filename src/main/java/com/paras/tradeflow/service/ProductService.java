package com.paras.tradeflow.service;

import com.paras.tradeflow.dto.ProductRequest;
import com.paras.tradeflow.dto.ProductResponse;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse getById(Long id);
    void deactivate(Long id);
    void activate(Long id);
}
