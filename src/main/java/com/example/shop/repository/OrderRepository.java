package com.example.shop.repository;

import com.example.shop.model.Order;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
  List<Order> findOrderByOrderNumberAndOrderName(String orderNumber, String orderName);

}
