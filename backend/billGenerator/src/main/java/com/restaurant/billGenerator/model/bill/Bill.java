package com.restaurant.billGenerator.model.bill;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "bill", schema = "restaurant")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private int id;

    @Column(name = "sub_total", nullable = false)
    private double subTotal;

    @Column(name = "tax", nullable = false)
    private double tax;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
}
