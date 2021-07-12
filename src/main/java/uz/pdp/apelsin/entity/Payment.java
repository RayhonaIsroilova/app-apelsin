package uz.pdp.apelsin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false)
    private double amount;

    @ManyToOne
    private Invoice invoice;

    public Payment(Timestamp time, double amount, Invoice invoice) {
        this.time = time;
        this.amount = amount;
        this.invoice = invoice;
    }
}
