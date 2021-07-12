package uz.pdp.apelsin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.apelsin.entity.Category;
import uz.pdp.apelsin.entity.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "select customer_id from customer join orders o on customer.id = o.customer_id where date_part('year', o.date) <> :year", nativeQuery = true)
    List<Customer> getCustomersWithoutOrders(String year);


    @Query(value = "select cr.country, count(o.id) as overall_orders  from customer cr join orders o on cr.id = o.customer_id where date_part('year', o.date)=:year group by cr.country", nativeQuery = true)
    List<?> getNumberOfProductsInYear(Integer year);
}
