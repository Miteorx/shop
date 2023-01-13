package com.example.shop.srevice;

import com.example.shop.dto.CustomerDto;
import com.example.shop.model.Customer;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.srevice.interfaces.CustomerService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer saveCustomer(CustomerDto customerDto) {
    Customer customer = new Customer(0L, customerDto.getName(), customerDto.getOrders());
    return customerRepository.save(customer);
  }

  public List<Customer> readAllCustomers() {
    return (List<Customer>) customerRepository.findAll();
  }

  public Customer findCustomerById(int id) {
    return customerRepository.findById((long) id).get();
  }

  public boolean updateCustomer(CustomerDto customerDto, int id) {
    if (customerRepository.existsById((long) id)) {
      Customer customer = new Customer((long) id, customerDto.getName(), customerDto.getOrders());
      customerRepository.save(customer);
      return true;
    }
    return false;
  }

  public boolean deleteCustomer(int id) {
    if (customerRepository.existsById((long) id)) {
      customerRepository.deleteById((long) id);
      return true;
    }
    return false;
  }

}
