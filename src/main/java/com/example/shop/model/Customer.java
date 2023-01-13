package com.example.shop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  @OneToMany(mappedBy = "customer",
      cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Order> orders;

//  @JsonCreator
//  public Customer (@JsonProperty("customer_id") Integer customer_id ) {
//    this.id = (long) customer_id;
//  }

  public Customer(Long id, String name, List<Order> orders) {
    this.id = id;
    this.name = name;
    this.orders = orders;
  }

  public Customer() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
