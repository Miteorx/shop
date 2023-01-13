package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Entity
@Table(name = "order_table")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(name = "order_number")
  private String orderNumber;
  @Column(name = "order_name")
  private String orderName;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
  @JoinColumn(name = "customer_id")
  @JsonBackReference
  private Customer customer;

  public Order(Long id, String orderNumber, String orderName, Customer customer) {
    this.id = id;
    this.orderNumber = orderNumber;
    this.orderName = orderName;
    this.customer = customer;
  }

  public Order() {

  }

  public String getOrderName() {
    return orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
