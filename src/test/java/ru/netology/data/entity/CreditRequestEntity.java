package ru.netology.data.entity;

import lombok.Data;
import ru.netology.data.Status;
import java.time.LocalDateTime;
import java.util.EnumSet;

@Data
public class CreditRequestEntity implements IValidation {

    private String id;
    private String bankId;
    private Status status;
    private LocalDateTime created;

    @Override
    public boolean isValid() {
        return (id != null) && (bankId != null)
                && EnumSet.allOf(Status.class).contains(status)
                && (created != null)
                ;
    }
}
