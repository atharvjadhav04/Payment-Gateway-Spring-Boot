package com.Payment_Gateway.service;

import com.Payment_Gateway.Repository.PaymentRepo;
import com.Payment_Gateway.entity.PaymentOrder;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Value("{razorpay.key_id}")
    private String keyId;

    @Value("{razorpay.key_secret}")
    private String keySecret;

    @Autowired
    private PaymentRepo paymentRepo;

    public String createOrder(PaymentOrder orderDetails) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(keyId,keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",(int)(orderDetails.getAmount()*100));
        orderRequest.put("Currency","INR");
        orderRequest.put("receipt","txn_"+ UUID.randomUUID());
        Order razorPayOrder = client.orders.create(orderRequest);
        System.out.println(razorPayOrder.toString());
        orderDetails.setOrderId(razorPayOrder.get("id"));
        orderDetails.setStatus("CREATED");
        orderDetails.setCreatedAt(LocalDateTime.now());
        paymentRepo.save(orderDetails);
        return razorPayOrder.toString();
    }
}
