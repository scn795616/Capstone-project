package com.crimsonlogic.freelancersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.freelancersystem.dto.WalletDTO;
import com.crimsonlogic.freelancersystem.entity.Wallet;
import com.crimsonlogic.freelancersystem.service.WalletService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletDTO> createWallet(@RequestBody WalletDTO walletDTO) {
        WalletDTO createdWallet = walletService.createWallet(walletDTO);
        return ResponseEntity.ok(createdWallet);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable String id) {
        Wallet wallet = walletService.getWalletById(id);
        return ResponseEntity.ok(wallet);
    }

    @PutMapping("add/{id}")
    public ResponseEntity<Wallet> updateWallet(@PathVariable String id, @RequestBody WalletDTO walletDTO) {
        Wallet updatedWallet = walletService.updateWallet(id, walletDTO);
        return ResponseEntity.ok(updatedWallet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable String id) {
        walletService.deleteWallet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WalletDTO>> getAllWallets() {
        List<WalletDTO> wallets = walletService.getAllWallets();
        return ResponseEntity.ok(wallets);
    }
}
