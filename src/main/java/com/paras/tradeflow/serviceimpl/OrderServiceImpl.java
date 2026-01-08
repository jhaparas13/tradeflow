package com.paras.tradeflow.serviceimpl;

import com.paras.tradeflow.entity.*;
import com.paras.tradeflow.repository.OrderRepository;
import com.paras.tradeflow.repository.ProductRepository;
import com.paras.tradeflow.repository.UserRepository;
import com.paras.tradeflow.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

//        Order order = new Order();
//        order.setUser(user);
//        order.setProduct(product);
//        order.setQuantity(quantity);
//        order.setPriceAtPurchase(product.getPrice());

        Order order = Order.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .status(OrderStatus.CREATED)
                .priceAtPurchase(product.getPrice())
                .build();

        orderRepository.save(order);
    }
}
