package com.marimon.sampleapp.impl;

import com.marimon.railways.themword.tries.Try;


@FunctionalInterface
public interface Ctlr {
  Try<Resp> handle(Req req);
}