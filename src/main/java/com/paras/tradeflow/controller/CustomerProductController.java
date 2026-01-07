package com.paras.tradeflow.controller;

import com.paras.tradeflow.dto.ProductResponse;
import com.paras.tradeflow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/stock")
    public ResponseEntity<Boolean> checkStock(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(productService.isInStock(id, quantity));
    }
}
