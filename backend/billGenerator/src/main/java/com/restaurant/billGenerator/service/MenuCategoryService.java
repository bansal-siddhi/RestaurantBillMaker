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
public class MenuCategoryService {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    public MenuCategory addMenuCategory(MenuCategory menuCategory){
        return menuCategoryRepository.save(menuCategory);
    }

    public List<MenuCategory> getCategories(){
        return menuCategoryRepository.findAll();
    }

    public Optional<MenuCategory> getCategory(String categoryName){
        return menuCategoryRepository.findByCategoryName(categoryName);
    }

    public MenuCategory updateCategory(String categoryName, String newCategoryName){
        Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository.findByCategoryName(categoryName);
        if(menuCategoryOptional.isPresent()){
            MenuCategory menuCategory = menuCategoryOptional.get();
            menuCategory.setCategoryName(newCategoryName);
            menuCategoryRepository.save(menuCategory);
            Optional<List<MenuItem>> menuItemsOptional =  menuItemRepository.findByMenuCategory_CategoryName(categoryName);
            if(menuItemsOptional.isPresent()){
                for(MenuItem item: menuItemsOptional.get()){
                    item.setMenuCategory(menuCategory);
                    menuItemRepository.save(item);
                }
            }
            return menuCategory;
        }
        else {
            throw new RuntimeException("Menu category with name " + categoryName + " not found");
        }
    }

    public void deleteCategory(String categoryName){
        Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository.findByCategoryName(categoryName);
        if(menuCategoryOptional.isPresent()){
            Optional<List<MenuItem>> menuItemsOptional =  menuItemRepository.findByMenuCategory_CategoryName(categoryName);
            menuItemsOptional.ifPresent(menuItems -> menuItemRepository.deleteAll(menuItems));
            menuCategoryRepository.delete(menuCategoryOptional.get());
        }
        else {
            throw new RuntimeException("Menu category with name " + categoryName + " not found");
        }
    }
}

