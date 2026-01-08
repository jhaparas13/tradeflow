package com.paras.tradeflow.repository;

import com.paras.tradeflow.entity.Order;
import com.paras.tradeflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
