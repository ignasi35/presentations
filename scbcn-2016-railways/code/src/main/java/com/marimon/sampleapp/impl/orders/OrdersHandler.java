package com.marimon.sampleapp.impl.orders;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;
import com.marimon.sampleapp.impl.Controller;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.db.DB;
import com.marimon.sampleapp.impl.resp.JSON;
import com.marimon.sampleapp.impl.resp.MethodNotAllowedException;
import com.marimon.sampleapp.impl.resp.NotFoundException;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class OrdersHandler implements com.sun.net.httpserver.HttpHandler {

  private Map<String, Controller> controllers = new HashMap<>();

  public OrdersHandler(DB db) {
    controllers.put("GET", new GetAllOrdersController(db));
//    controllers.put("PUT", new AddOrderController(db));
//    controllers.put("DELETE", new RemoveOrderController(db));
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    Try<Resp> maybeResp =
        prepareController.apply(httpExchange)
            .flatMap(controller ->
                parseRequest
                    .apply(httpExchange)
                    .flatMap(controller::handle));

    deliverResp(httpExchange, maybeResp);

  }

  private Function<HttpExchange, Try<Controller>> prepareController =
      httpExc -> Tries.to(() -> {
        Controller x = controllers.get(httpExc.getRequestMethod());
        if (x == null) {
          throw new MethodNotAllowedException();
        } else {
          return x;
        }
      });

  private Function<HttpExchange, Try<Req>> parseRequest = httpExch -> Tries.to(Req::new);


  // --------------------------------------------------------------------------------------------------------

  private void deliverResp(HttpExchange httpExchange, Try<Resp> response) throws IOException {
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
  }
}
