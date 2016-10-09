package com.marimon.sampleapp.impl.orders;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;
import com.marimon.sampleapp.impl.Ctlr;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.resp.MethodNotAllowedException;
import com.marimon.sampleapp.impl.resp.NotFoundException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class OrdersHandler implements com.sun.net.httpserver.HttpHandler {

  private Map<String, Ctlr> controllers = new HashMap<String, Ctlr>() {
    {
      put("GET", new GetAllOrdersController());
    }
  };

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    Try<Resp> maybeResp =
        prepareController.apply(httpExchange)
            .flatMap(ctlr ->
                parseRequest
                    .apply(httpExchange)
                    .flatMap(ctlr::handle));

    deliverResp(httpExchange, maybeResp);

  }

  private Function<HttpExchange, Try<Ctlr>> prepareController =
      httpExc -> Tries.to(() -> {
        System.out.println("Loading controller");
        Ctlr x = controllers.get(httpExc.getRequestMethod());
        if (x == null) {
          System.out.println("controller is missing");
          throw new MethodNotAllowedException();
        } else {
          return x;
        }
      });

  private Function<HttpExchange, Try<Req>> parseRequest = httpExch -> Tries.to(Req::new);


  private void deliverResp(HttpExchange httpExchange, Try<Resp> response) throws IOException {
    try (OutputStream responseBody = httpExchange.getResponseBody()) {
      try {
        response.get();
        httpExchange.sendResponseHeaders(200, 0);
      } catch (Throwable e) {
        int statusCode = handleFailure(responseBody, e);
        httpExchange.sendResponseHeaders(statusCode, 0);
      }
    }
  }

  private int handleFailure(OutputStream responseBody, Throwable e) throws IOException {
    if (e.getCause() instanceof MethodNotAllowedException) {
      return 405;
    } else if (e.getCause() instanceof NotFoundException) {
      return 404;
    } else {
      return 500;
    }
  }
}
