package com.restaurant.billGenerator.respository;

import com.restaurant.billGenerator.model.menu.MenuCategory;
import com.restaurant.billGenerator.model.menu.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {
    Optional<MenuCategory> findByCategoryName(String categoryName);
}
