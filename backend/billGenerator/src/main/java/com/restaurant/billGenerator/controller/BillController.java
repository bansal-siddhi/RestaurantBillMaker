package com.restaurant.billGenerator.controller;

import com.restaurant.billGenerator.model.bill.Bill;
import com.restaurant.billGenerator.model.bill.BillDetail;
import com.restaurant.billGenerator.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody List<BillDetail> billDetails) {
        Bill createdBill = billService.createBill(billDetails);
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }
}
