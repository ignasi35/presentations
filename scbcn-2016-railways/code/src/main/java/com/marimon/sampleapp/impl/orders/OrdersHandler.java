package com.marimon.sampleapp.impl.orders;

import com.marimon.sampleapp.impl.Ctlr;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.resp.MethodNotAllowed;
import com.marimon.sampleapp.impl.resp.OK;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrdersHandler implements com.sun.net.httpserver.HttpHandler {

  private Map<String, Ctlr> controllers = new HashMap<String, Ctlr>() {
    {
      put("GET", new GetAllOrdersController());
    }
  };
  private Ctlr defaultCtlr = req -> new MethodNotAllowed();

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    Ctlr ctlr = controllers.getOrDefault(httpExchange.getRequestMethod(), defaultCtlr);
    Req req = buildReq(httpExchange);
    Resp resp = ctlr.handle(req);
    deliverResp(httpExchange, resp);
  }

  private void deliverResp(HttpExchange httpExchange, Resp resp) throws IOException {
    if (resp instanceof OK) {
      httpExchange.sendResponseHeaders(200, 0);
      httpExchange.getResponseBody().close();
    } else if (resp instanceof MethodNotAllowed) {
      httpExchange.sendResponseHeaders(405, 0);
      httpExchange.getResponseBody().close();
    } else {
      httpExchange.sendResponseHeaders(503, 0);
      httpExchange.getResponseBody().close();
    }
  }

  private Req buildReq(HttpExchange httpExchange) {
    return new Req();
  }
}
