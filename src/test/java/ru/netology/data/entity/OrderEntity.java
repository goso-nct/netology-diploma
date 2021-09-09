package ru.netology.data.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderEntity implements IValidation {

    private String id;
    private String creditId;
    private String paymentId;
    private LocalDateTime created;

    @Override
    public boolean isValid() {
        return (id != null)
                && (creditId != null || paymentId != null)
                && (created != null)
                ;
    }
}