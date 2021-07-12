package uz.pdp.apelsin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.apelsin.entity.Detail;
import uz.pdp.apelsin.entity.Invoice;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query(value = "select * from invoice where issued>due", nativeQuery = true)
    List<Invoice> getExpiredInvoices();

    @Query(value =
            "select i.id as invoice_id, py.amount as reimburse from invoice i\n" +
                    "    join payment py on i.id=py.invoice_id group by i.id, py.amount, i.id having count(py.invoice_id)<>0;", nativeQuery = true)
    List<?> getOverpaidInvoices();
}
