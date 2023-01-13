package com.example.shop.dto;

import jakarta.validation.constraints.NotNull;

public class OrderDto {

  @NotNull(message = "Number should be not null")
  private String number;
  @NotNull(message = "Order name should be not null")
  private String orderName;
  @NotNull(message = "Order should have the customer id")
  private String customerId;

  public OrderDto(String number, String orderName, String customer) {
    this.number = number;
    this.orderName = orderName;
    this.customerId = customer;
  }

  public String getOrderName() {
    return orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
}
