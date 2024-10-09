package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
