package com.paras.tradeflow.controller;

import com.paras.tradeflow.dto.AdminOrderResponse;
import com.paras.tradeflow.entity.OrderStatus;
import com.paras.tradeflow.service.AdminOrderService;
import com.paras.tradeflow.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<AdminOrderResponse>> getAllOrders(@RequestParam(required = false)OrderStatus status) {
        if (status == null) {
            return ResponseEntity.ok(adminOrderService.getAllOrders());
        }
        return  ResponseEntity.ok(adminOrderService.getOrdersByStatus(status));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId, null, true);
        return ResponseEntity.noContent().build();
    }

}
