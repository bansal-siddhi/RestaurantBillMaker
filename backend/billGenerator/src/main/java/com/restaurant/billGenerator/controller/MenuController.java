    package com.restaurant.billGenerator.controller;


    import com.restaurant.billGenerator.model.menu.MenuItem;
    import com.restaurant.billGenerator.respository.MenuItemRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/menu")
    public class MenuController {

        @Autowired
        private MenuItemRepository menuItemRepository;

        @GetMapping("/menuItems")
        public ResponseEntity<List<MenuItem>> getMenuItems() {
            List<MenuItem> menuItems = menuItemRepository.findAll();
            if (menuItems != null && !menuItems.isEmpty()) {
                return new ResponseEntity<>(menuItems, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @PostMapping()
        public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem){
            try{
                menuItemRepository.save(menuItem);
                return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @PostMapping("/bulk")
        public ResponseEntity<List<MenuItem>> addMenuItems(@RequestBody List<MenuItem> menuItems){
            try{
                menuItemRepository.saveAll(menuItems);
                return new ResponseEntity<>(menuItems, HttpStatus.CREATED   );
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
