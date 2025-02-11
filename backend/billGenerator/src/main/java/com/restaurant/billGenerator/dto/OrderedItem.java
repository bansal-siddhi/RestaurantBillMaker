package com.restaurant.billGenerator.dto;

import com.restaurant.billGenerator.model.menu.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class OrderedItem {
    private MenuItem menuItem;
    private int quantity;
}
