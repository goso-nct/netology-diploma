package ru.netology.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CreditRequestEntity extends CommonEntity {

    private String bankId;

    @Override
    public boolean isValid() {
        return super.isValid()
                && (bankId != null)
                ;
    }
}
