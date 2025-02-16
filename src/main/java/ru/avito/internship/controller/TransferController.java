package ru.avito.internship.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.avito.internship.domain.dto.TransferRequest;
import ru.avito.internship.service.TransferService;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/buy/{item}")
    public HttpStatusCode buyItem(@PathVariable String item) {
        transferService.buyMerch(item);
        return HttpStatus.OK;
    }

    @PostMapping("/sendCoin")
    public HttpStatusCode sendCoin(@RequestBody @Valid @NotNull TransferRequest request) {
        transferService.makeTransfer(request.toUser(), request.amount());
        return HttpStatus.OK;
    }
}
