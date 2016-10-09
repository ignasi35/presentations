package com.marimon.sampleapp.impl.orders;

import com.marimon.railways.themword.tries.Tries;
import com.marimon.railways.themword.tries.Try;
import com.marimon.sampleapp.impl.Ctlr;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.resp.OK;


public class GetAllOrdersController implements Ctlr {
  @Override
  public Try<Resp> handle(Req req) {
    return Tries.to(() -> new OK());
  }
}
