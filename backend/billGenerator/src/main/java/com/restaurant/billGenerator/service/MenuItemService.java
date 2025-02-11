package com.restaurant.billGenerator.service;

import com.restaurant.billGenerator.model.menu.MenuCategory;
import com.restaurant.billGenerator.model.menu.MenuItem;
import com.restaurant.billGenerator.respository.MenuCategoryRepository;
import com.restaurant.billGenerator.respository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
    @Autowired
    MenuItemRepository menuItemRepository;

    public MenuItem addMenuItem(MenuItem menuItem){
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getMenuItems(){
        return menuItemRepository.findAll();
    }

    public MenuItem updatePriceOfMenuItem(double price, String name){
        Optional<MenuItem> menuItemOptional = menuItemRepository.findByItemName(name);
        if(menuItemOptional.isPresent()){
            MenuItem menuItem = menuItemOptional.get();
            menuItem.setItemPrice(price);
            return menuItemRepository.save(menuItem);
        }else {
            throw new RuntimeException("Menu item with name " + name + " not found");
        }
    }

    public void deleteMenuItem(String name){
        Optional<MenuItem> menuItemOptional = menuItemRepository.findByItemName(name);
        menuItemOptional.ifPresent(menuItem -> menuItemRepository.delete(menuItem));
    }
}
