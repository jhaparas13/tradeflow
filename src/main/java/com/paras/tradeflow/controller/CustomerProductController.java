package com.paras.tradeflow.controller;

import com.paras.tradeflow.dto.PageResponse;
import com.paras.tradeflow.dto.ProductResponse;
import com.paras.tradeflow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class CustomerProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> customerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
            ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductResponse> page1 = productService.getAllForCustomer(pageable);

        return ResponseEntity.ok(
                new PageResponse<>(
                        page1.getContent(),
                        page1.getNumber(),
                        page1.getSize(),
                        page1.getTotalElements(),
                        page1.getTotalPages(),
                        page1.isLast()
                )
        );
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Boolean> checkStock(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(productService.isInStock(id, quantity));
    }
}
