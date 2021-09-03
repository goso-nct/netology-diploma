package ru.netology.data.entity;

import lombok.Data;
import ru.netology.data.Status;
import java.time.LocalDateTime;

@Data
public class PaymentEntity {

    private String id;
    private String transactionId;
    private int amount;
    private Status status;
    private LocalDateTime created;

}
