package com.marimon.sampleapp.impl;

import com.marimon.sampleapp.impl.db.DB;
import com.marimon.sampleapp.impl.db.InMemH2DB;
import com.marimon.sampleapp.impl.orders.OrdersHandler;
import com.sun.net.httpserver.HttpHandler;


public class AppContext {
  private HttpHandler ordersHandler;

  public AppContext() throws Exception {

    DB db = new InMemH2DB();

    ordersHandler = new OrdersHandler(db);
  }

  public HttpHandler getOrdersHandler() {
    return ordersHandler;
  }
}
