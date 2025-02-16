package ru.avito.internship.domain.dto;

import java.util.List;

public record TransfersHistory(
        List<ReceiptElement> received,
        List<TransferRequest> sent
) { }
