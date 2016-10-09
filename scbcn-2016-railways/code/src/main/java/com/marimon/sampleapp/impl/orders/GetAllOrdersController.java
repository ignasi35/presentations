package com.marimon.sampleapp.impl.orders;

import com.marimon.sampleapp.impl.Ctlr;
import com.marimon.sampleapp.impl.Req;
import com.marimon.sampleapp.impl.Resp;
import com.marimon.sampleapp.impl.resp.OK;


public class GetAllOrdersController implements Ctlr {
  @Override
  public Resp handle(Req req) {
    return new OK();
  }
}
