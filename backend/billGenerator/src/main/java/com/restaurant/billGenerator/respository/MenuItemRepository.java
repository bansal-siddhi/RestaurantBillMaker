package com.restaurant.billGenerator.respository;

import com.restaurant.billGenerator.model.menu.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    Optional<MenuItem> findByItemName(String itemName);

    Optional<List<MenuItem>> findByMenuCategory_CategoryName(String categoryName);
}
