package com.example.shop.controller;

import com.example.shop.dto.CustomerDto;
import com.example.shop.dto.OrderDto;
import com.example.shop.model.Customer;
import com.example.shop.model.Order;
import com.example.shop.srevice.OrderServiceImpl;
import com.example.shop.srevice.exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderServiceImpl orderService;

  public OrderController(OrderServiceImpl orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrder(@PathVariable @Valid int id)
      throws CustomerNotFoundException {
    return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
  }

  @GetMapping("/{number}/{name}")
  public ResponseEntity<List<Order>> getOrder(@PathVariable @Valid String number,
      @PathVariable @Valid String name)
      throws CustomerNotFoundException {
    return new ResponseEntity<>(orderService.findOrderByNumberAndName(number, name), HttpStatus.OK);
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<Order>> getAllOrders() {
    return ResponseEntity.ok(orderService.readAllOrders());
  }

  @PostMapping("/save")
  public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderDto orderDto)
      throws CustomerNotFoundException {
    return new ResponseEntity<>(orderService.saveOrder(orderDto), HttpStatus.CREATED);
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<Boolean> updateOrder(@RequestBody @Valid OrderDto orderDto,
      @PathVariable int id) throws CustomerNotFoundException {
    return new ResponseEntity<>(orderService.updateOrder(orderDto, id), HttpStatus.OK);
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity<Boolean> deleteCustomer(@PathVariable @Valid int id)
      throws CustomerNotFoundException {
    return new ResponseEntity<>(orderService.deleteOrder(id), HttpStatus.OK);
  }
}
