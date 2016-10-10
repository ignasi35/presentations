package com.marimon.sampleapp.impl;

import com.marimon.railways.themword.tries.Try;
import com.marimon.sampleapp.impl.resp.JSON;
import com.marimon.sampleapp.impl.resp.MethodNotAllowedException;
import com.marimon.sampleapp.impl.resp.NotFoundException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;


public abstract class Handler implements HttpHandler {

  protected void deliverResp(HttpExchange httpExchange, Try<Resp> response) throws IOException {
    try { // handles the exchange
      writeResponse(httpExchange, response);
    } finally {
      httpExchange.close();
    }
  }

  private void writeResponse(HttpExchange httpExchange, Try<Resp> response) throws IOException {
    try { // handles the Try<Resp>
      Resp resp = response.get();
      writeResponseHeaders(httpExchange, resp);
      writeResponseBody(httpExchange.getResponseBody(), resp);
    } catch (Throwable e) {
      int statusCode = handleFailure(e);
      httpExchange.sendResponseHeaders(statusCode, 0);
    }
  }

  private void writeResponseHeaders(HttpExchange httpExchange, Resp resp) throws IOException {
    if (resp instanceof JSON) {
      httpExchange.getResponseHeaders().put("Content-Type", Arrays.asList("application/json; charset=utf-8"));
    }
    httpExchange.sendResponseHeaders(200, 0);
  }

  private void writeResponseBody(OutputStream responseBody, Resp resp) throws IOException {
    if (resp.payload().isPresent()) {
      String payload = resp.payload().get();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(responseBody));
      writer.write(payload);
      writer.flush();
    }
  }

  private int handleFailure(Throwable e) {
    if (e.getCause() instanceof MethodNotAllowedException) {
      return 405;
    } else if (e.getCause() instanceof NotFoundException) {
      return 404;
    } else {
      e.printStackTrace();
      return 500;
    }
  }}
