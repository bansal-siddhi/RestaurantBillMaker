package com.restaurant.billGenerator.controller;


import com.restaurant.billGenerator.model.menu.MenuCategory;
import com.restaurant.billGenerator.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menuCategories")
public class MenuCategoryController {

    @Autowired
    private MenuCategoryService menuCategoryService;

    @GetMapping("/")
    public ResponseEntity<List<MenuCategory>> getMenuCategories(){
        List<MenuCategory> menuCategories = menuCategoryService.getCategories();
        if(menuCategories!=null && !menuCategories.isEmpty())
            return new ResponseEntity<>(menuCategories, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<MenuCategory> addMenuCategory(@RequestBody String categoryName){
        try{
            Optional<MenuCategory> menuCategoryOptional = menuCategoryService.getCategory(categoryName);
            if(menuCategoryOptional.isEmpty()){
                MenuCategory menuCategory = new MenuCategory();
                menuCategory.setCategoryName(categoryName);
                menuCategoryService.addMenuCategory(menuCategory);
                return new ResponseEntity<>(menuCategory,HttpStatus.CREATED);
            }
            return new ResponseEntity<>(menuCategoryOptional.get(), HttpStatus.CONFLICT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{categoryName}")
    public ResponseEntity<MenuCategory> updateMenuCategory(@PathVariable String categoryName, @RequestBody String newCategoryName){
        MenuCategory menuCategory = menuCategoryService.updateCategory(categoryName, newCategoryName);
        if (menuCategory != null) {
            return new ResponseEntity<>(menuCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{categoryName}")
    public ResponseEntity<MenuCategory> deleteMenuCategory(@PathVariable String categoryName){
        menuCategoryService.deleteCategory(categoryName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
