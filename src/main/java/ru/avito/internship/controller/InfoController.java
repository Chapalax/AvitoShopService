package ru.avito.internship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avito.internship.domain.dto.InfoResponse;
import ru.avito.internship.service.TransferService;
import ru.avito.internship.service.UserService;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {

    private final UserService userService;
    private final TransferService transferService;

    @GetMapping
    public ResponseEntity<InfoResponse> getInfo() {
        var user = userService.getCurrentUser();
        return ResponseEntity.ok(new InfoResponse(
                user.getBalance(),
                transferService.getUserInventory(user.getId()),
                transferService.getUserTransfersHistory(user.getId())
        ));
    }
}
