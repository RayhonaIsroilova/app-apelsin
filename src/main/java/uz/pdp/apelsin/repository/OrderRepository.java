package uz.pdp.apelsin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.apelsin.entity.Invoice;
import uz.pdp.apelsin.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select * from orders where customer_id is null and date<:year",nativeQuery = true)
    List<Order> getOrdersWithoutDetails(String year);

    @Query(value = "select c.id,c.name,max(o.date) as recent from orders o join customer c on o.customer_id=c.id group by 1",nativeQuery = true)
    List<?> getCustomerLastOrder();
}
