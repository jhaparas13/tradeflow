package com.paras.tradeflow.service;

import com.paras.tradeflow.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    public void placeOrder(String email, Long productId, int quantity);
    List<OrderResponse> getMyOrders(String email);
}
