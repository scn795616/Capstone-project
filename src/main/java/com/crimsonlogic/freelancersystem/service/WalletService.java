package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.WalletDTO;
import com.crimsonlogic.freelancersystem.entity.Wallet;

import java.util.List;

public interface WalletService {
    WalletDTO createWallet(WalletDTO walletDTO);
    Wallet getWalletById(String id);
    Wallet updateWallet(String id, WalletDTO walletDTO);
    void deleteWallet(String id);
	List<WalletDTO> getAllWallets();
}

