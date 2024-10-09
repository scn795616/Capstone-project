package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.PaymentDTO;
import com.crimsonlogic.freelancersystem.entity.Payment;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);
    PaymentDTO getPaymentById(String id);
    PaymentDTO updatePayment(String id, PaymentDTO paymentDTO);
    void deletePayment(String id);
	List<PaymentDTO> getAllPayments();
}
