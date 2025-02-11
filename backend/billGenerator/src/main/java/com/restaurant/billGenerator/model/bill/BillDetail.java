package com.restaurant.billGenerator.model.bill;

import com.restaurant.billGenerator.model.menu.MenuItem;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bill_detail", schema = "restaurant")
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id")
    private int id;

    @Column(name = "bill_id")
    private int bill_id;

    @Column(name = "menu_item_id")
    private int menuItemId;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
