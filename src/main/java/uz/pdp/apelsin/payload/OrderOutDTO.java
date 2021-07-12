package uz.pdp.apelsin.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.apelsin.entity.Detail;
import uz.pdp.apelsin.entity.Order;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutDTO {
    private Order order;
    private List<Detail> detailList;
}
