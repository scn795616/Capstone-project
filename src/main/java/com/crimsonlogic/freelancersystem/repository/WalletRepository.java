package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.Wallet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

	Optional<Wallet> findByUserDetails_UserId(String id);
}
