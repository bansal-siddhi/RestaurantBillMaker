package com.restaurant.billGenerator.service;

import com.restaurant.billGenerator.model.bill.Bill;
import com.restaurant.billGenerator.model.bill.BillDetail;
import com.restaurant.billGenerator.model.menu.MenuItem;
import com.restaurant.billGenerator.respository.BillDetailRepository;
import com.restaurant.billGenerator.respository.BillRepository;
import com.restaurant.billGenerator.respository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    BillDetailRepository billDetailRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Transactional
    public Bill createBill(List<BillDetail> billDetailList) {
        Bill bill = new Bill();
        double subTotal = 0;
        for (BillDetail billDetail : billDetailList) {
            Optional<MenuItem> menuItem = menuItemRepository.findByItemName(billDetail.getMenuItem().getItemName());
            if (menuItem.isPresent()) {
                subTotal += menuItem.get().getItemPrice() * billDetail.getQuantity();
                billDetail.setMenuItem(menuItem.get());
            } else {
                throw new IllegalArgumentException("MenuItem cannot be null in BillDetail");
            }
        }

        double taxRate = 0.05;
        double tax = subTotal * taxRate;
        double totalAmount = subTotal + tax;

        bill.setSubTotal(subTotal);
        bill.setTax(tax);
        bill.setTotalAmount(totalAmount);

        Bill savedBill = billRepository.save(bill);

        for (BillDetail detail : billDetailList) {
            detail.setBill(savedBill);
            billDetailRepository.save(detail);
        }

        return savedBill;
    }
}
