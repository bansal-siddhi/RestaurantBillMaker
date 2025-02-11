package com.restaurant.billGenerator.model.menu;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "menu", schema = "restaurant")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private int id;
    @Column(name="item_name")
    private String itemName;
    @Column(name="item_price")
    private double itemPrice;
    @ManyToOne
    @JoinColumn(name = "menu_category_name", referencedColumnName = "category_name")
    private MenuCategory menuCategory;
}
