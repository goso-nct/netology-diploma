package ru.netology.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import java.time.LocalDateTime;

@Data
public class OrderEntity {

    private String id;
    private String creditId;
    private String paymentId;
    private LocalDateTime created;

}
