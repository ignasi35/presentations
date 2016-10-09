package com.marimon.sampleapp.impl.orders;

/**
 *
 */
public class Order {

  private final int id;
  private final String name;
  private final String username;

  public Order(int id, String name, String username) {
    this.id = id;
    this.name = name;
    this.username = username;
  }

  public int id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String customer() {
    return username;
  }


  public static String asJSON(Order order) {
    return String.format("{\"id\": %d, \"name\": \"%s\", \"customer\": \"%s\"}", order.id(), order.name(), order.customer());
  }

}
