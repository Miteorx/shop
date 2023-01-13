package com.example.shop.dto;

import com.example.shop.model.Order;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CustomerDto {
  @NotNull(message = "Name should be not null")
  private String name;
  @Nullable
  private List<Order> orders;

  public CustomerDto() {
  }

  public CustomerDto(String name, List<Order> orders) {
    this.name = name;
    this.orders = orders;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }
}
