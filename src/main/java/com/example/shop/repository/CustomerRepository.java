package com.example.shop.repository;

import com.example.shop.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
  Customer findCustomerById(Long id);

}
