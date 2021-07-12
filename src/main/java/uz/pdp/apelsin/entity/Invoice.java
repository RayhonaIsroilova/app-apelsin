package uz.pdp.apelsin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date issued;

    @Column(nullable = false)
    private Date due;

    @Column(nullable = false)
    private double amount;

    @OneToOne(optional = false)
    private Order order;

    boolean isPaid = false;
}
