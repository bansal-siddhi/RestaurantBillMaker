package com.restaurant.billGenerator.controller;


import com.restaurant.billGenerator.model.menu.MenuCategory;
import com.restaurant.billGenerator.respository.MenuCategoryRepository;
import com.restaurant.billGenerator.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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

//    @Autowired
//    private MenuCategoryRepository menuCategoryRepository;
//
//    @GetMapping("/categories")
//    public ResponseEntity<List<MenuCategory>> getMenuCategories() {
//        List<MenuCategory> menuCategories = menuCategoryRepository.findAll();
//        if (menuCategories != null && !menuCategories.isEmpty()) {
//            return new ResponseEntity<>(menuCategories, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
    @PostMapping("/add")
    public ResponseEntity<MenuCategory> addMenuCategory(@RequestBody String categoryName){
        try{
            Optional<MenuCategory> menuCategoryOptional = menuCategoryService.getCategory(categoryName);
            if(!menuCategoryOptional.isPresent()){
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
        menuCategoryService.updateCategory(categoryName, newCategoryName);
    }
}
