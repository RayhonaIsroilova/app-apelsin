package uz.pdp.apelsin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.apelsin.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select c.id, c.name from product as p join category c on p.category_id = c.id where p.id=:productId", nativeQuery = true)
    Category findByProductId(Integer productId);

    boolean existsByName(String name);
}
