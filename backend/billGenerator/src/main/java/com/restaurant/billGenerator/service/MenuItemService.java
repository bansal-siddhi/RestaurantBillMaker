package com.restaurant.billGenerator.service;

import com.restaurant.billGenerator.model.menu.MenuCategory;
import com.restaurant.billGenerator.model.menu.MenuItem;
import com.restaurant.billGenerator.respository.MenuCategoryRepository;
import com.restaurant.billGenerator.respository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    public MenuItem addMenuItem(MenuItem menuItem) {
        String categoryName = menuItem.getMenuCategory().getCategoryName();
        Optional<MenuCategory> menuCategory = menuCategoryRepository.findByCategoryName(categoryName);
        if (menuCategory.isPresent()) {
            menuItem.setMenuCategory(menuCategory.get());
            return menuItemRepository.save(menuItem);
        } else
            throw new RuntimeException("Menu category with name " + categoryName + " not found");
    }

    public List<MenuItem> addMenuItems(List<MenuItem> menuItems) {
        List<MenuItem> menuItemList = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            String categoryName = menuItem.getMenuCategory().getCategoryName();
            Optional<MenuCategory> menuCategory = menuCategoryRepository.findByCategoryName(categoryName);
            if (menuCategory.isPresent()) {
                menuItem.setMenuCategory(menuCategory.get());
                menuItemList.add(menuItem);
            } else
                throw new RuntimeException("Menu category with name " + categoryName + " not found");
        }
        return menuItemRepository.saveAll(menuItemList);
    }

    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> getMenuItem(String itemName) {
        return menuItemRepository.findByItemName(itemName);
    }

    public MenuItem updatePriceOfMenuItem(double price, String name) {
        Optional<MenuItem> menuItemOptional = menuItemRepository.findByItemName(name);
        if (menuItemOptional.isPresent()) {
            MenuItem menuItem = menuItemOptional.get();
            menuItem.setItemPrice(price);
            return menuItemRepository.save(menuItem);
        } else {
            throw new RuntimeException("Menu item with name " + name + " not found");
        }
    }

    public void deleteMenuItem(String name) {
        Optional<MenuItem> menuItemOptional = menuItemRepository.findByItemName(name);
        menuItemOptional.ifPresent(menuItem -> menuItemRepository.delete(menuItem));
    }
}
