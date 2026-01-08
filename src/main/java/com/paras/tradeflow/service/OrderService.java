package com.paras.tradeflow.service;

public interface OrderService {
    public void placeOrder(String email, Long productId, int quantity);
}
