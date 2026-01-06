package com.paras.tradeflow.controller;

import com.paras.tradeflow.dto.ProductResponse;
import com.paras.tradeflow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class CustomerProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> customerList() {
        return ResponseEntity.ok(productService.getAllForCustomer());
    }
}
