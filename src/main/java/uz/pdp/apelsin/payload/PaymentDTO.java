package uz.pdp.apelsin.payload;

import lombok.Data;

@Data
public class PaymentDTO {
    private Integer invoiceId;
    private double amount;
}
