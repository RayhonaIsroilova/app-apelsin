package uz.pdp.apelsin.payload;

import lombok.Data;

@Data
public class OrderEditDTO {
    private Integer quantity, customerId, productId, invoiceId, detailedId;
}
