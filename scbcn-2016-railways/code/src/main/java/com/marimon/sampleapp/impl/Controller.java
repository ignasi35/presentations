package com.marimon.sampleapp.impl;

import com.marimon.railways.themword.tries.Try;


@FunctionalInterface
public interface Controller {
  Try<Resp> handle(Req req);
}
