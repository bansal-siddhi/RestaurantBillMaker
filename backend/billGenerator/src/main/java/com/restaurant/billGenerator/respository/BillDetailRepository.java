package com.restaurant.billGenerator.respository;

import com.restaurant.billGenerator.model.bill.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
}
