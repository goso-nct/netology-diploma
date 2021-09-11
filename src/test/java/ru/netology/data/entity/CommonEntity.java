package ru.netology.data.entity;

import lombok.Data;
import ru.netology.data.Status;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Data
public class CommonEntity {

    private String id;
    private Status status;
    private LocalDateTime created;

    public boolean isValid() {
        return (id != null)
                && EnumSet.allOf(Status.class).contains(status)
                && (created != null)
                ;
    }
}
