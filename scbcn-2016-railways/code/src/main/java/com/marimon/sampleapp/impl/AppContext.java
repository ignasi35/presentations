package com.marimon.sampleapp.impl;

import com.marimon.sampleapp.impl.orders.OrdersHandler;
import com.sun.net.httpserver.HttpHandler;


public class AppContext {
  private HttpHandler ordersHandler;

  public AppContext() {
    // TODO Add DB
    // TODO Add Validation
    ordersHandler = new OrdersHandler();
  }

  public HttpHandler getOrdersHandler() {
    return ordersHandler;
  }
}
