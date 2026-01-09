package com.paras.tradeflow.controller;

import com.paras.tradeflow.dto.AdminOrderResponse;
import com.paras.tradeflow.entity.OrderStatus;
import com.paras.tradeflow.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public ResponseEntity<List<AdminOrderResponse>> getAllOrders(@RequestParam(required = false)OrderStatus status) {
        if (status == null) {
            return ResponseEntity.ok(adminOrderService.getAllOrders());
        }
        return  ResponseEntity.ok(adminOrderService.getOrdersByStatus(status));
    }
}
