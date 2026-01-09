package com.paras.tradeflow.service;

import com.paras.tradeflow.dto.AdminOrderResponse;
import com.paras.tradeflow.entity.OrderStatus;

import java.util.List;

public interface AdminOrderService {
    public List<AdminOrderResponse> getAllOrders();
    public List<AdminOrderResponse> getOrdersByStatus(OrderStatus status);
}
