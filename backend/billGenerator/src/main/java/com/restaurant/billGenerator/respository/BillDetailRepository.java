package com.restaurant.billGenerator.respository;

import com.restaurant.billGenerator.model.bill.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
}
