package com.paras.tradeflow.serviceimpl;

import com.paras.tradeflow.dto.OrderResponse;
import com.paras.tradeflow.entity.*;
import com.paras.tradeflow.repository.OrderRepository;
import com.paras.tradeflow.repository.ProductRepository;
import com.paras.tradeflow.repository.UserRepository;
import com.paras.tradeflow.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void placeOrder(String email, Long productId, int quantity) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not Found"));

        Product product = productRepository.findByIdForUpdate(productId).orElseThrow(() -> new RuntimeException("Product not Found"));

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new RuntimeException("Product is INACTIVE");
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient Stock");
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);

        Order order = Order.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .status(OrderStatus.CREATED)
                .priceAtPurchase(product.getPrice())
                .build();

        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void cancelOrder(Long orderId, String requesterEmail, boolean isAdmin) {

        Order order = orderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new RuntimeException("Order not Found"));

        if (!isAdmin && !order.getUser().getEmail().equals(requesterEmail)) {
            throw new RuntimeException("Not allowed to cancel this Order");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order cannot be Cancelled");
        }

        Product product = order.getProduct();
        product.setStockQuantity(product.getStockQuantity() + order.getQuantity());
        order.setStatus(OrderStatus.CANCELLED);
    }

    @Override
    public List<OrderResponse> getMyOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        return orderRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .productId(order.getProduct().getId())
                        .productName(order.getProduct().getName())
                        .quantity(order.getQuantity())
                        .priceAtPurchase(order.getPriceAtPurchase())
                        .status(order.getStatus())
                        .createdAt(order.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public void handlePaymentFailure(Long orderId) {
        Order order = orderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new RuntimeException("Order not Found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order status is not 'CREATED' for handling payment Failure");
        }

        Product product = order.getProduct();
        product.setStockQuantity(product.getStockQuantity() + order.getQuantity());
        order.setStatus(OrderStatus.CANCELLED);
    }

    @Override
    @Transactional
    public void handlePaymentSuccess(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
        order.setStatus(OrderStatus.COMPLETED);
    }
}
