package com.marimon.sampleapp.impl.orders;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;
import com.marimon.sampleapp.impl.Controller;
import com.marimon.sampleapp.impl.Handler;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.db.DB;
import com.marimon.sampleapp.impl.resp.MethodNotAllowedException;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class OrdersHandler extends Handler {

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

}
