package ru.avito.internship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avito.internship.domain.dto.InventoryElement;
import ru.avito.internship.domain.dto.ReceiptElement;
import ru.avito.internship.domain.dto.TransferRequest;
import ru.avito.internship.domain.dto.TransfersHistory;
import ru.avito.internship.domain.exception.InsufficientFundsException;
import ru.avito.internship.domain.exception.MerchNotFoundException;
import ru.avito.internship.domain.exception.UserNotFoundException;
import ru.avito.internship.domain.model.Inventory;
import ru.avito.internship.domain.model.InventoryKey;
import ru.avito.internship.domain.model.Transfer;
import ru.avito.internship.repository.InventoryRepository;
import ru.avito.internship.repository.MerchRepository;
import ru.avito.internship.repository.TransferRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final InventoryRepository inventoryRepository;
    private final TransferRepository transferRepository;
    private final MerchRepository merchRepository;
    private final UserService userService;

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Transfer saveTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Transactional
    public void buyMerch(String item) {
        var merch = merchRepository.findByItem(item)
                .orElseThrow(() -> new MerchNotFoundException("Product not found: " + item));

        var user = userService.getCurrentUser();
        if (user.getBalance() < merch.getPrice()) {
            throw new InsufficientFundsException("Insufficient funds in the account: " + user.getBalance());
        }

        user.setBalance(user.getBalance() - merch.getPrice());
        userService.save(user);

        var inventoryKey = new InventoryKey(user.getId(), merch.getId());
        var inventory = inventoryRepository.findById(inventoryKey)
                .orElse(new Inventory(inventoryKey, 0));

        inventory.setAmount(inventory.getAmount() + 1);
        saveInventory(inventory);
    }

    @Transactional
    public void makeTransfer(String receiverName, Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be positive: " + amount);
        }

        var sender = userService.getCurrentUser();
        if (sender.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in the account: " + sender.getBalance());
        }

        try{
            var receiver = userService.getByUsername(receiverName);
            var transfer = new Transfer();
            transfer.setSender(sender.getId());
            transfer.setRecipient(receiver.getId());
            transfer.setAmount(amount);
            saveTransfer(transfer);

            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            userService.save(sender);
            userService.save(receiver);
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("User not found: " + receiverName);
        }
    }

    public TransfersHistory getUserTransfersHistory(Long userId) {
        List<ReceiptElement> receipts = new ArrayList<>();
        List<TransferRequest> shipments = new ArrayList<>();

        for (Transfer transfer : transferRepository.findAllByRecipient(userId)) {
            receipts.add(new ReceiptElement(
                    userService.getById(transfer.getSender()).getUsername(),
                    transfer.getAmount()
            ));
        }

        for (Transfer transfer : transferRepository.findAllBySender(userId)) {
            shipments.add(new TransferRequest(
                    userService.getById(transfer.getRecipient()).getUsername(),
                    transfer.getAmount()
            ));
        }

        return new TransfersHistory(receipts, shipments);
    }

    public List<InventoryElement> getUserInventory(Long userId) {
        List<InventoryElement> inventoryList = new ArrayList<>();

        for (Inventory elem : inventoryRepository.findAllById_UserId(userId)) {
            var merch = merchRepository.findById(elem.getId().getMerchId())
                    .orElseThrow(() -> new MerchNotFoundException("Product not found: " + elem.getId().getMerchId()));

            inventoryList.add(new InventoryElement(merch.getItem(), elem.getAmount()));
        }
        return inventoryList;
    }
}
