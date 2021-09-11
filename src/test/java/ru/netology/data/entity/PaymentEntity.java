package ru.netology.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PaymentEntity extends CommonEntity {

    private String transactionId;
    private int amount;

    @Override
    public boolean isValid() {
        return super.isValid()
                && (transactionId != null)
                && (amount != 0)
                ;
    }
}
