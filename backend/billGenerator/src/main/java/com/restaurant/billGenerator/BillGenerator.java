package com.restaurant.billGenerator;

public class BillGenerator {
    public static int count = 0;

//    public static void main(String[] args) throws Exception {
//        System.out.println("Enter owner or customer: ");
//        Scanner sc = new Scanner(System.in);
//        String choice = sc.next();
//        switch (choice) {
//            case "o":
//                System.out.println("Do you want to enter a new category or menu item?(Enter c or m): ");
//                String menuOption = sc.next();
//                if (menuOption.trim().equals("m")) {
//                    MenuItemService menuItemService = new MenuItemService(MenuItemImpl.getInstance());
//                    menuItemService.addMenuItem();
//                } else if (menuOption.trim().equals("c")) {
//                    MenuCategoryService menuCategoryService = new MenuCategoryService(MenuCategoryImpl.getInstance());
//                    menuCategoryService.addCategory();
//                } else {
//                    System.out.println("Enter a valid option, either c or m");
//                }
//                ;
//                break;
//            case "c":
//                BillService billService = new BillService(BillDAOImpl.getInstance(), MenuItemImpl.getInstance());
//                List<MenuItem> menu = billService.generateMenu();
//                billService.generateBill(menu);
//                break;
//            default:
//                System.out.println("Invalid choice! Please enter a valid option.");
//                break;
//        }
//    }
}
