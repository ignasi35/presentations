package com.marimon.railways.impl;


import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class OrdersHandler implements com.sun.net.httpserver.HttpHandler {
  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    switch (httpExchange.getRequestMethod()) {
//      case "GET":
      default:
//        httpExchange.getResponseHeaders().
        httpExchange.sendResponseHeaders(404, 0);
        httpExchange.getResponseBody().close();
        break;
    }

  }
}
