package com.paras.tradeflow.service;

import com.paras.tradeflow.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    void placeOrder(String email, Long productId, int quantity);
    List<OrderResponse> getMyOrders(String email);
    void cancelOrder(Long orderId, String requesterEmail, boolean isAdmin);
    void handlePaymentSuccess(Long orderId);
    void handlePaymentFailure(Long orderId);
}
