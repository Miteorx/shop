package com.example.shop.srevice.exceptions;

public class CustomerNotFoundException extends Exception {

  public CustomerNotFoundException() {
    super("Customer not found with this id");
  }
}
