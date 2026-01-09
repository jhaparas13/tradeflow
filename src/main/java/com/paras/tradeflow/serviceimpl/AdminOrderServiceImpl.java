package com.paras.tradeflow.serviceimpl;

import com.paras.tradeflow.dto.AdminOrderResponse;
import com.paras.tradeflow.dto.OrderResponse;
import com.paras.tradeflow.entity.Order;
import com.paras.tradeflow.entity.OrderStatus;
import com.paras.tradeflow.repository.OrderRepository;
import com.paras.tradeflow.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<AdminOrderResponse> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public List<AdminOrderResponse> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::map)
                .toList();
    }

    private AdminOrderResponse map(Order order) {
        return AdminOrderResponse.builder()
                .orderId(order.getId())
                .customerEmail(order.getUser().getEmail())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .quantity(order.getQuantity())
                .priceAtPurchase(order.getPriceAtPurchase())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
