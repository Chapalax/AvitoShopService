package ru.avito.internship.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.avito.internship.domain.dto.TransferRequest;
import ru.avito.internship.service.TransferService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/buy/{item}")
    public void buyItem(@PathVariable String item) {
        transferService.buyMerch(item);
    }

    @PostMapping("/sendCoin")
    public void sendCoin(@RequestBody @Valid @NotNull TransferRequest request) {
        transferService.makeTransfer(request.toUser(), request.amount());
    }
}
