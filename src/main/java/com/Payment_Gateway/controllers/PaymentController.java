package com.Payment_Gateway.controllers;

import com.Payment_Gateway.entity.PaymentOrder;
import com.Payment_Gateway.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

        @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody PaymentOrder order){
        try{
            String response = paymentService.createOrder(order);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Creating Order");
        }
    }
}
