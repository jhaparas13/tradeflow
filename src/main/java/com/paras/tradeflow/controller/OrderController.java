package com.paras.tradeflow.controller;

import com.paras.tradeflow.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> placeOrder(@RequestParam Long productId, @RequestParam int quantity, @AuthenticationPrincipal UserDetails userDetails) {
        orderService.placeOrder(userDetails.getUsername(), productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
