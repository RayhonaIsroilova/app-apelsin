package uz.pdp.apelsin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.apelsin.entity.Category;
import uz.pdp.apelsin.entity.Detail;

import java.util.List;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Integer> {
    List<Detail> findByProductId(Integer product_id);

    List<Detail> findByOrderId(Integer order_id);

    @Query(value = "select sum(d.quantity), d.product_id from detail d group by d.product_id having sum(d.quantity)>10",nativeQuery = true)
    List<?> getHighDemandProducts();

    @Query(value = "select dt.product_id,pr.price from detail dt " +
            "join product pr on dt.product_id=pr.id " +
            "group by pr.price,dt.product_id " +
            "having avg(quantity)>=8;",nativeQuery = true)
    List<?> getBulkProducts();
}
