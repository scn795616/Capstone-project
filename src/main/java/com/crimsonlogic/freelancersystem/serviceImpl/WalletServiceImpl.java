package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.dto.WalletDTO;
import com.crimsonlogic.freelancersystem.entity.Wallet;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.WalletRepository;
import com.crimsonlogic.freelancersystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WalletDTO createWallet(WalletDTO walletDTO) {
        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        walletRepository.save(wallet);
        return modelMapper.map(wallet, WalletDTO.class);
    }

    @Override
    public Wallet getWalletById(String id) {
        Wallet wallet = walletRepository.findByUserDetails_UserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
        return wallet;
    }

    @Override
    public Wallet updateWallet(String id, WalletDTO walletDTO) {
        Wallet existingWallet =walletRepository.findByUserDetails_UserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
        existingWallet.setBalance(walletDTO.getAmount());
        walletRepository.save(existingWallet);
        return existingWallet;
    }

    @Override
    public void deleteWallet(String id) {
        walletRepository.deleteById(id);
    }

	@Override
	public List<WalletDTO> getAllWallets() {
		return walletRepository.findAll()
                .stream()
                .map(wallet -> modelMapper.map(wallet, WalletDTO.class))
                .collect(Collectors.toList());
	}
}
