package com.marimon.sampleapp;

import com.marimon.sampleapp.impl.AppContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {

  public static void main(String[] args) throws IOException {
    int port = 9000;
    HttpServer httpServer = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

    AppContext context = new AppContext();

    HttpHandler ordersHandler = context.getOrdersHandler();

    httpServer.createContext("/orders", ordersHandler);
    System.out.println("Starting server on port " + port);
    httpServer.start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.stop(0)));

  }

}
