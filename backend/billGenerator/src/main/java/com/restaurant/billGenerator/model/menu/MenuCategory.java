package com.restaurant.billGenerator.model.menu;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menu_category", schema = "restaurant")
public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="category_name")
    private String categoryName;
}
