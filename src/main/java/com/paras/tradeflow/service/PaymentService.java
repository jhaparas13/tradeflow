package com.paras.tradeflow.service;

import com.paras.tradeflow.entity.Order;
import com.paras.tradeflow.entity.Payment;

public interface PaymentService {
    Payment initiatePayment(Long orderId);
    Payment markPaymentSuccess(Long orderId);
    Payment markPaymentFailed(Long orderId);
    Payment getByOrderId(Long orderId);

}
