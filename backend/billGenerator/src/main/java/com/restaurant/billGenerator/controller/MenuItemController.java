package com.restaurant.billGenerator.controller;


import com.restaurant.billGenerator.model.menu.MenuItem;
import com.restaurant.billGenerator.respository.MenuItemRepository;
import com.restaurant.billGenerator.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menuItems")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/")
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        List<MenuItem> menuItems = menuItemService.getMenuItems();
        if (menuItems != null && !menuItems.isEmpty()) {
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getItem")
    public ResponseEntity<MenuItem> getMenuItem(@RequestParam("itemName") String itemName) {
        Optional<MenuItem> menuItem = menuItemService.getMenuItem(itemName);
        return menuItem.map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        try {
            MenuItem newItem = menuItemService.addMenuItem(menuItem);
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<MenuItem>> addMenuItems(@RequestBody List<MenuItem> menuItems) {
        try {
            List<MenuItem> newItems = menuItemService.addMenuItems(menuItems);
            return new ResponseEntity<>(newItems, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("updatePrice")
    public ResponseEntity<MenuItem> updatePriceOfMenuItem(@RequestParam("menuItem") String menuItem, @RequestBody float menuPrice) {
        MenuItem updatedMenuItem = menuItemService.updatePriceOfMenuItem(menuPrice, menuItem);
        if (updatedMenuItem != null)
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<MenuItem> deleteMenuItem(@RequestParam("menuItemName") String menuItemName) {
        menuItemService.deleteMenuItem(menuItemName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
