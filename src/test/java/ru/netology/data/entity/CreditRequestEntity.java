package ru.netology.data.entity;

import lombok.Data;
import ru.netology.data.Status;
import java.time.LocalDateTime;

@Data
public class CreditRequestEntity {

    private String id;
    private String bankId;
    private Status status;
    private LocalDateTime created;

}
