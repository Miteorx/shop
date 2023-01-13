package com.example.shop.srevice;

import com.example.shop.dto.OrderDto;
import com.example.shop.model.Customer;
import com.example.shop.model.Order;
import com.example.shop.repository.CustomerRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.srevice.exceptions.CustomerNotFoundException;
import com.example.shop.srevice.interfaces.OrderService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;

  public OrderServiceImpl(OrderRepository orderRepository,
      CustomerRepository customerRepository) {
    this.orderRepository = orderRepository;
    this.customerRepository = customerRepository;
  }

  public Order saveOrder(OrderDto orderDto) throws CustomerNotFoundException {
    Customer customer = customerRepository.findCustomerById(Long.valueOf(orderDto.getCustomerId()));
    if (customer == null) {
      throw new CustomerNotFoundException();
    }
    Order order = new Order(0L, orderDto.getNumber(), orderDto.getOrderName(), customer);
    return orderRepository.save(order);
  }

  public List<Order> readAllOrders() {
    return (List<Order>) orderRepository.findAll();
  }

  public Order findOrderById(int id) {
    return orderRepository.findById((long) id).get();
  }

  public List<Order> findOrderByNumberAndName(String number, String name) {
    return orderRepository.findOrderByOrderNumberAndOrderName(number, name);
  }

  public boolean updateOrder(OrderDto orderDto, int id) {
    if (orderRepository.existsById((long) id)) {
      Customer customer = customerRepository.findCustomerById(
          Long.valueOf(orderDto.getCustomerId()));
      Order order = new Order((long) id, orderDto.getNumber(), orderDto.getOrderName(), customer);
      orderRepository.save(order);
      return true;
    }
    return false;
  }

  public boolean deleteOrder(int id) {
    if (orderRepository.existsById((long) id)) {
      orderRepository.deleteById((long) id);
      return true;
    }
    return false;
  }
}
