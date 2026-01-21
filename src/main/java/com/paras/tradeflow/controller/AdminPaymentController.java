package com.paras.tradeflow.controller;

import com.paras.tradeflow.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/payments")
@RequiredArgsConstructor
public class AdminPaymentController {

    private final PaymentService paymentService;

    @PostMapping("/success/{orderId}")
    public ResponseEntity<Void> success(@PathVariable Long orderId) {
        paymentService.markPaymentSuccess(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/fail/{orderId}")
    public ResponseEntity<Void> fail(@PathVariable Long orderId) {
        paymentService.markPaymentFailed(orderId);
        return ResponseEntity.noContent().build();
    }
}
