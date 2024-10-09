package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.PaymentDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Payment;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.PaymentRepository;
import com.crimsonlogic.freelancersystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    @Override
    public PaymentDTO getPaymentById(String id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return modelMapper.map(payment, PaymentDTO.class);
    }

    @Override
    public PaymentDTO updatePayment(String id, PaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        modelMapper.map(paymentDTO, existingPayment);
        paymentRepository.save(existingPayment);
        return modelMapper.map(existingPayment, PaymentDTO.class);
    }

    @Override
    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }

	@Override
	public List<PaymentDTO> getAllPayments() {
		return paymentRepository.findAll()
                .stream()
                .map(pay -> modelMapper.map(pay, PaymentDTO.class))
                .collect(Collectors.toList());
	}
}
