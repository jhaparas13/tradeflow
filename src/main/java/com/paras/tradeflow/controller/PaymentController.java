package com.paras.tradeflow.controller;

import com.paras.tradeflow.entity.Payment;
import com.paras.tradeflow.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<Payment> initiate(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.initiatePayment(orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getByOrderId(orderId));
    }
}
