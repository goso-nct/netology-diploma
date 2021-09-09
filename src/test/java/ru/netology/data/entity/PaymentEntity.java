package ru.netology.data.entity;

import lombok.Data;
import ru.netology.data.Status;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Data
public class PaymentEntity implements IValidation {

    private String id;
    private String transactionId;
    private int amount;
    private Status status;
    private LocalDateTime created;

    @Override
    public boolean isValid() {
        return (id != null) && (transactionId != null) && (amount != 0)
                && EnumSet.allOf(Status.class).contains(status)
                && (created != null)
                ;
    }
}
