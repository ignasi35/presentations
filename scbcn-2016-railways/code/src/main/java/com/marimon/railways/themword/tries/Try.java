package com.marimon.railways.themword.tries;

import java.util.function.Function;

public interface Try<T> {

  <R> Try<R> map(Function<T, R> f);

  <R> Try<R> flatMap(Function<T, Try<R>> f);

  T get();

  boolean isSuccess();

  default boolean isFailure() {
    return !isSuccess();
  }
}

