package com.example.shop.controller;

import com.example.shop.dto.CustomerDto;
import com.example.shop.model.Customer;
import com.example.shop.srevice.CustomerServiceImpl;
import com.example.shop.srevice.exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerServiceImpl customerService;


  public CustomerController(CustomerServiceImpl customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable @Valid int id)
      throws CustomerNotFoundException {
    return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<Customer>> getAllCustomers() {
    return ResponseEntity.ok(customerService.readAllCustomers());
  }

  @PostMapping("/save")
  public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerDto customerDto) {
    return new ResponseEntity<>(customerService.saveCustomer(customerDto), HttpStatus.CREATED);
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<Boolean> updateCustomer(@RequestBody @Valid CustomerDto customerDto,
      @PathVariable int id) throws CustomerNotFoundException {
    return new ResponseEntity<>(customerService.updateCustomer(customerDto, id), HttpStatus.OK);
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity<Boolean> deleteCustomer(@PathVariable @Valid int id)
      throws CustomerNotFoundException {
    return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
  }
}
