package com.restaurant.billGenerator.model.bill;

import com.restaurant.billGenerator.model.menu.MenuCategory;
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

    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name="menu_item_name", referencedColumnName = "item_name")
    private MenuItem menuItem;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
