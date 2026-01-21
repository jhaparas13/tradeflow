package com.paras.tradeflow.serviceimpl;

import com.paras.tradeflow.entity.Order;
import com.paras.tradeflow.entity.Payment;
import com.paras.tradeflow.entity.PaymentStatus;
import com.paras.tradeflow.repository.OrderRepository;
import com.paras.tradeflow.repository.PaymentRepository;
import com.paras.tradeflow.service.OrderService;
import com.paras.tradeflow.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Override
    public Payment initiatePayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Id not Found"));

        Payment payment= Payment.builder()
                .order(order)
                .amount(order.getPriceAtPurchase())
                .status(PaymentStatus.PENDING)
                .transactionRef(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment markPaymentSuccess(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.SUCCESS)
            return payment;

        if (payment.getStatus() == PaymentStatus.FAILED)
            throw new RuntimeException("Payment has already Failed");

        payment.setStatus(PaymentStatus.SUCCESS);
        orderService.handlePaymentSuccess(orderId);

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment markPaymentFailed(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.FAILED)
            return payment;

        if (payment.getStatus() == PaymentStatus.SUCCESS)
            throw new RuntimeException("Payment has already Completed");

        payment.setStatus(PaymentStatus.FAILED);
        orderService.handlePaymentFailure(orderId);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
